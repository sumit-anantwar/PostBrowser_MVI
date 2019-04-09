package com.sumitanantwar.postsbrowser.mobile.ui.postlist

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.BindView
import butterknife.OnClick
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler
import com.ivianuu.contributer.conductor.ConductorInjection
import com.jakewharton.rxbinding2.widget.RxTextView
import com.sumitanantwar.mvi.MviController
import com.sumitanantwar.postsbrowser.mobile.R
import com.sumitanantwar.postsbrowser.mobile.ui.postdetails.PostDetailsController
import com.sumitanantwar.postsbrowser.mobile.ui.postdetails.PostDetailsControllerInstance
import com.sumitanantwar.postsbrowser.mobile.util.HeightProperty
import com.sumitanantwar.presentation.postslist.PostsListIntent
import com.sumitanantwar.presentation.postslist.PostsListIntent.*
import com.sumitanantwar.presentation.postslist.PostsListViewModel
import com.sumitanantwar.presentation.postslist.PostsListViewState
import com.sumitanantwar.presentation.model.Post
import com.sumitanantwar.repository.model.User
import com.sumitanantwar.repository.scheduler.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class PostsListController :
    MviController<PostsListIntent, PostsListViewState>(),
    PostListAdapterCallBackListener {

    //======= ButterKnife Bindings =======
    @BindView(R.id.filter_container)
    lateinit var filterContiner: ViewGroup

    @BindView(R.id.filter_bar)
    lateinit var filterBar: ViewGroup

    @BindView(R.id.layout_filter)
    lateinit var filterPanel: ViewGroup

    @BindView(R.id.edit_text_userid)
    lateinit var editTextUserId: EditText

    @BindView(R.id.edit_text_title)
    lateinit var editTextTitle: EditText

    @BindView(R.id.edit_text_body)
    lateinit var editTextBody: EditText

    @BindView(R.id.recycler_posts)
    lateinit var postsRecyclerView: RecyclerView

    @BindView(R.id.swiperefresh_posts)
    lateinit var swipeRefreshLayout: SwipeRefreshLayout

    @BindView(R.id.text_error)
    lateinit var errorTextView: TextView


    //======= Injections =======
    @Inject
    lateinit var viewModel: PostsListViewModel

    @Inject
    lateinit var postsListAdapter: PostListAdapter

    @Inject
    lateinit var schedulerProvider: SchedulerProvider


    //======= Publishers =======
    private val loadPostsWithFilterPublisher = PublishSubject.create<LoadPostsWithFilterIntent>()
    private val loadAllUsersPublisher = PublishSubject.create<LoadAllUsersIntent>()


    //======= Abstract =======
    override val layoutId: Int = R.layout.posts_list_controller


    //======= Controller Lifecycle =======
    override fun onContextAvailable(context: Context) {
        super.onContextAvailable(context)

        ConductorInjection.inject(this)
    }

    override fun onViewBound(view: View) {

        // Set Controller Title
        setTitle(applicationContext?.getString(R.string.title_posts_list))
        setDisplayHomeAsUpEnabled(false)

        // Setup RecyclerView
        postsRecyclerView.layoutManager = LinearLayoutManager(this.applicationContext)
        postsRecyclerView.adapter = postsListAdapter
        postsListAdapter.setCallBackListener(this)

        // Swipe Refresh Listener
        swipeRefreshLayout.setOnRefreshListener {
            fetchPosts()
        }

        val dividerDecoration =
            DividerItemDecoration(this.applicationContext, DividerItemDecoration.VERTICAL)
        dividerDecoration.setDrawable(
            ContextCompat.getDrawable(
                this.applicationContext!!,
                R.drawable.divider_shape
            )!!
        )
        postsRecyclerView.addItemDecoration(dividerDecoration)

        rxSetup()
        bindIntents()

        // Load all the Users at launch
        loadAllUsersPublisher.onNext(LoadAllUsersIntent)
    }

    override fun handleBack(): Boolean {
        if (filterPanel.visibility == View.VISIBLE) {
            toggleFilterPanelVisibility()

            return true
        }

        return super.handleBack()
    }

    //======= Button Click Listeners =======
    @OnClick(R.id.button_filter)
    fun onClickFilterButton() {
        toggleFilterPanelVisibility()
    }


    //======= Private Methods =======
    private fun rxSetup() {

        val userIdObservable = RxTextView.textChanges(editTextUserId)
            .map { it.toString() }
            .distinctUntilChanged()

        val titleObservable = RxTextView.textChanges(editTextTitle)
            .map { it.toString() }
            .distinctUntilChanged()

        val bodyObservable = RxTextView.textChanges(editTextBody)
            .map { it.toString() }
            .distinctUntilChanged()

        Observables.combineLatest(userIdObservable, titleObservable, bodyObservable)
            .debounce(300, TimeUnit.MILLISECONDS)
            .observeOn(schedulerProvider.ui())
            .subscribe {
                val userId = it.first
                val title = it.second
                val body = it.third

                fetchPostsWithFilter(userId, title, body)

            }.addTo(disposables)

    }

    private fun fetchPosts() {

        val userId = editTextUserId.text.toString()
        val title = editTextTitle.text.toString()
        val body = editTextBody.text.toString()

        fetchPostsWithFilter(userId, title, body)
    }

    private fun fetchPostsWithFilter(userId: String, title: String, body: String) {

        loadPostsWithFilterPublisher.onNext(LoadPostsWithFilterIntent(userId, title, body))
    }

    /** Toggles the Visibility of the Filter Panel */
    private fun toggleFilterPanelVisibility() {

        // Check the currnt state of the Filter Panel
        val isFilterPanelHidden = (filterPanel.visibility == View.INVISIBLE)

        // Make the panel visible, so that the animation can be seen
        filterPanel.visibility = View.VISIBLE

        // Store the current dimensions of the views to be animated
        val filterContainerHeight = filterContiner.height.toFloat()
        val filterBarHeight = filterBar.height.toFloat()
        val filterPanelHeight = filterPanel.height.toFloat()

        // Calculate the new positions
        var filterPanelY = filterBarHeight
        var newFilterContainerHeight = filterBarHeight + filterPanelHeight
        if (!isFilterPanelHidden) {
            filterPanelY = -filterPanelHeight
            newFilterContainerHeight = filterBarHeight
        }

        // Set the view layer types to Hardware for smoother animation
        filterPanel.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        filterContiner.setLayerType(View.LAYER_TYPE_HARDWARE, null)

        // Create an animator set and play the animations together
        AnimatorSet().apply {
            duration = 300

            playTogether(
                ObjectAnimator.ofFloat(filterPanel, "y", filterPanelY),
                ObjectAnimator.ofFloat(
                    filterContiner,
                    HeightProperty(),
                    filterContainerHeight,
                    newFilterContainerHeight
                )
            )

            addListener(
                object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        filterPanel.setLayerType(View.LAYER_TYPE_NONE, null)
                        filterContiner.setLayerType(View.LAYER_TYPE_NONE, null)

                        filterPanel.visibility =
                            if (isFilterPanelHidden) View.VISIBLE else View.INVISIBLE
                    }
                }
            )
        }.start()
    }

    //======= PostListAdapterCallbackListener =======
    override fun onClickPost(post: Post, user: User) {

        val changeHandler = HorizontalChangeHandler()

        router.pushController(
            RouterTransaction.with(PostDetailsControllerInstance(post, user))
                .pushChangeHandler(changeHandler)
                .popChangeHandler(changeHandler)
        )
    }

    //======= MVI =======
    fun bindIntents() {
        viewModel.states().subscribe{
            this.render(it)
        }.addTo(disposables)

        viewModel.processIntents(intents())
    }

    override fun intents(): Observable<PostsListIntent> {

        return Observable.merge(
            loadPostsWithFilterIntent(),
            loadAllUsersIntent()
        )
    }

    override fun render(state: PostsListViewState) {

        swipeRefreshLayout.isRefreshing = state.isLoading

        if (state.isLoading) {
            postsRecyclerView.visibility = if (state.posts.isEmpty()) View.GONE else View.VISIBLE
            errorTextView.visibility = View.GONE

            return
        }

        val error = state.error
        if (error != null) {

            Toast.makeText(applicationContext, error.localizedMessage, Toast.LENGTH_SHORT).show()

            postsRecyclerView.visibility = View.GONE
            errorTextView.visibility = View.VISIBLE

            errorTextView.text = applicationContext?.getString(R.string.error_fetching_posts)

            return
        }

        if (!state.posts.isEmpty()) {
            postsRecyclerView.visibility = View.VISIBLE
            errorTextView.visibility = View.GONE

            postsListAdapter.updatePosts(state.posts, state.users)
        }
        else {
            errorTextView.visibility = View.VISIBLE
            postsRecyclerView.visibility = View.GONE

            errorTextView.text = applicationContext?.getString(R.string.error_empty_posts)
        }
    }

    //======= Observables from Publishers =======
    private fun loadPostsWithFilterIntent(): Observable<LoadPostsWithFilterIntent> {
        return loadPostsWithFilterPublisher
    }

    private fun loadAllUsersIntent() : Observable<LoadAllUsersIntent> {
        return loadAllUsersPublisher
    }
}