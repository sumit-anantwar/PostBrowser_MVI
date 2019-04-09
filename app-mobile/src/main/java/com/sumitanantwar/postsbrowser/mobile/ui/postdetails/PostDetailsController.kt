package com.sumitanantwar.postsbrowser.mobile.ui.postdetails

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import butterknife.BindView
import com.bumptech.glide.Glide
import com.ivianuu.contributer.conductor.ConductorInjection
import com.sumitanantwar.mvi.MviController
import com.sumitanantwar.postsbrowser.mobile.R
import com.sumitanantwar.presentation.model.Post
import com.sumitanantwar.presentation.postdetails.PostDetailsIntent
import com.sumitanantwar.presentation.postdetails.PostDetailsIntent.LoadPostCommentsIntent
import com.sumitanantwar.presentation.postdetails.PostDetailsViewModel
import com.sumitanantwar.presentation.postdetails.PostDetailsViewState
import com.sumitanantwar.repository.model.User
import com.sumitanantwar.repository.scheduler.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject

private val ARG_POST = "Arg_Post"
private val ARG_USER = "Arg_User"

fun PostDetailsControllerInstance(post: Post, user: User): PostDetailsController {
    val bundle = Bundle().apply {
        putParcelable(ARG_POST, post)
        putParcelable(ARG_USER, user)
    }

    return PostDetailsController(bundle)
}

class PostDetailsController(args: Bundle) :
    MviController<PostDetailsIntent, PostDetailsViewState>(args) {

    //======= ButterKnife Binders =======

    @BindView(R.id.image_profile)
    lateinit var imageViewProfile: ImageView

    @BindView(R.id.text_post_id)
    lateinit var textPostId: TextView

    @BindView(R.id.text_post_title)
    lateinit var textPostTitle: TextView

    @BindView(R.id.text_post_body)
    lateinit var textPostBody: TextView

    @BindView(R.id.text_username)
    lateinit var textUserName: TextView

    @BindView(R.id.text_comment_count)
    lateinit var textCommentCount: TextView

    @BindView(R.id.progress_comments)
    lateinit var progressBarComments: ProgressBar


    //======= Injections =======
    @Inject
    lateinit var viewModel: PostDetailsViewModel

    @Inject
    lateinit var schedulerProvider: SchedulerProvider


    //======= Publishers =======
    private val loadPostsCommentsPublisher = PublishSubject.create<LoadPostCommentsIntent>()


    //======= Private Properties =======
    private val post: Post
    private val user: User


    //======= Anstract =======
    override val layoutId: Int = R.layout.post_details_controller


    init {
        val p = args.getParcelable<Post>(ARG_POST)
        val u = args.getParcelable<User>(ARG_USER)

        if (p == null || u == null) {
            throw IllegalArgumentException("This controller requires Post and User values")
        }

        post = p
        user = u
    }

    //======= Controller Lifecycle =======
    override fun onContextAvailable(context: Context) {
        super.onContextAvailable(context)

        ConductorInjection.inject(this)
    }

    override fun onViewBound(view: View) {
        setTitle("Post Details")
        setDisplayHomeAsUpEnabled(true)


        textPostTitle.text = post.title
        textPostBody.text = post.body
        textPostId.text = post.id.toString()
        textUserName.text = user.username

        Glide.with(activity!!)
            .load(user.profileImageUrl)
            .into(imageViewProfile)

        bindIntents()

        loadPostsCommentsPublisher.onNext(LoadPostCommentsIntent(post.id))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            router.popCurrentController()
            return true
        }

        return false
    }


    //======= MVI =======
    fun bindIntents() {
        viewModel.states().subscribeBy(
            onNext = {
                this.render(it)
            },
            onError = {
                Timber.e(it)
            }
        ).addTo(disposables)

        viewModel.processIntents(intents())
    }

    override fun intents(): Observable<PostDetailsIntent> {
        return loadPostCommentsIntent().cast(PostDetailsIntent::class.java)
    }

    override fun render(state: PostDetailsViewState) {

        if (state.isLoading) {
            progressBarComments.visibility = View.VISIBLE
            textCommentCount.visibility = View.INVISIBLE
        }
        else {
            progressBarComments.visibility = View.GONE
            textCommentCount.visibility = View.VISIBLE
        }

        if (!state.comments.isEmpty()) {
            textCommentCount.text = "Comments Count : ${state.comments.count()}"
        }
        else {
            textCommentCount.text = "No Comments for this Post"
        }

        if (state.error != null) {
            textCommentCount.text = "Error loading comments"
        }
    }

    //======= Observables from Publishers =======
    private fun loadPostCommentsIntent(): Observable<LoadPostCommentsIntent> {
        return loadPostsCommentsPublisher
    }
}