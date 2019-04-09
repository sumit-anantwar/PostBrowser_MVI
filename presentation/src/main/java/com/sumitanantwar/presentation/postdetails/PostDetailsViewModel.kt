package com.sumitanantwar.presentation.postdetails

import androidx.lifecycle.ViewModel
import com.sumitanantwar.mvi.base.MviViewModel
import com.sumitanantwar.presentation.postdetails.PostDetailsAction.LoadPostCommentsAction
import com.sumitanantwar.presentation.postdetails.PostDetailsIntent.LoadPostCommentsIntent
import com.sumitanantwar.presentation.postdetails.PostDetailsResult.LoadPostCommentsResult
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class PostDetailsViewModel @Inject constructor(
    private val actionProcessorHolder: PostDetailsProcessorHolder
) : ViewModel(), MviViewModel<PostDetailsIntent, PostDetailsViewState> {

    private val intentSubject: PublishSubject<PostDetailsIntent> = PublishSubject.create()
    private val statesObservable: Observable<PostDetailsViewState> = compose()


    //======= MviViewModel =======
    override fun processIntents(intents: Observable<PostDetailsIntent>) {

        intents.subscribe(intentSubject)
    }

    override fun states(): Observable<PostDetailsViewState> {
        return statesObservable
    }


    //======= Private =======
    private fun compose(): Observable<PostDetailsViewState> {

        return intentSubject
            .map(this::actionFromIntent)
            .compose(actionProcessorHolder.actionProcessor)
            .scan(PostDetailsViewState.idle(), reducer)
            .distinctUntilChanged()
            .replay(1)
            .autoConnect(0)
    }


    private fun actionFromIntent(intent: PostDetailsIntent): PostDetailsAction {

        return when (intent) {
            is LoadPostCommentsIntent -> LoadPostCommentsAction(intent.postId)
        }
    }

    companion object {

        private val reducer =
            BiFunction { previousState: PostDetailsViewState, result: PostDetailsResult ->
                return@BiFunction when (result) {
                    is LoadPostCommentsResult -> when (result) {
                        is LoadPostCommentsResult.Success -> previousState.copy(
                            isLoading = false,
                            comments = result.comments,
                            error = null
                        )
                        is LoadPostCommentsResult.Failure -> previousState.copy(
                            isLoading = false,
                            error = result.error
                        )
                        is LoadPostCommentsResult.Loading -> previousState.copy(
                            isLoading = true,
                            error = null
                        )
                    }
                }
            }
    }


}