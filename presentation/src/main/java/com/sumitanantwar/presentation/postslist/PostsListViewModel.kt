package com.sumitanantwar.presentation.postslist

import androidx.lifecycle.ViewModel
import com.sumitanantwar.mvi.base.MviViewModel
import com.sumitanantwar.presentation.postslist.PostsListAction.*
import com.sumitanantwar.presentation.postslist.PostsListIntent.*
import com.sumitanantwar.presentation.postslist.PostsListResult.LoadAllUsersResult
import com.sumitanantwar.presentation.postslist.PostsListResult.LoadPostsResult
import com.sumitanantwar.presentation.util.notOfType
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class PostsListViewModel @Inject constructor(
    private val actionProcessorHolder: PostsListProcessorHolder
) : ViewModel(), MviViewModel<PostsListIntent, PostsListViewState> {

    private val intentSubject: PublishSubject<PostsListIntent> = PublishSubject.create()
    private val statesObservable: Observable<PostsListViewState> = compose()

    //======= MviViewModel =======
    override fun processIntents(intents: Observable<PostsListIntent>) {

        intents.subscribe(intentSubject)
    }

    override fun states(): Observable<PostsListViewState> {
        return statesObservable
    }


    //======= Private =======
    private fun compose(): Observable<PostsListViewState> {
        return intentSubject
            .map(this::actionFromIntent)
            .compose(actionProcessorHolder.actionProcessor)
            .scan(PostsListViewState.idle(), reducer)
            .distinctUntilChanged()
            .replay(1)
            .autoConnect(0)
    }


    private fun actionFromIntent(intent: PostsListIntent): PostsListAction {

        return when (intent) {
            is LoadPostsWithFilterIntent    -> LoadPostsWithFilterAction(intent.userId, intent.title, intent.body)
            is LoadAllUsersIntent           -> LoadAllUsersAction
        }
    }


    companion object {

        private val reducer =
            BiFunction { previousState: PostsListViewState, result: PostsListResult ->
                return@BiFunction when (result) {
                    is LoadPostsResult -> when (result) {
                        is LoadPostsResult.Success -> previousState.copy(isLoading = false, posts = result.posts, error = null)
                        is LoadPostsResult.Failure -> previousState.copy(isLoading = false, error = result.error)
                        is LoadPostsResult.Loading -> previousState.copy(isLoading = true, error = null)
                    }
                    is LoadAllUsersResult -> when (result) {
                        is LoadAllUsersResult.Success -> previousState.copy(isLoading = false, users = result.users, error = null)
                        is LoadAllUsersResult.Failure -> previousState.copy(isLoading = false, error = result.error)
                        is LoadAllUsersResult.Loading -> previousState.copy(isLoading = true, error = null)
                    }
                }
            }
    }
}