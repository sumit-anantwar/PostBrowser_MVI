package com.sumitanantwar.presentation.postdetails

import com.sumitanantwar.presentation.mapper.CommentMapper
import com.sumitanantwar.presentation.postdetails.PostDetailsAction.LoadPostCommentsAction
import com.sumitanantwar.presentation.postdetails.PostDetailsResult.LoadPostCommentsResult
import com.sumitanantwar.repository.base.MainRepository
import com.sumitanantwar.repository.scheduler.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import java.lang.IllegalArgumentException
import javax.inject.Inject

class PostDetailsProcessorHolder @Inject constructor(
    private val mainRepository: MainRepository,
    private val schedulerProvider: SchedulerProvider,
    private val commentMapper: CommentMapper
) {

    private val loadPostCommentsProcessor =
        ObservableTransformer<LoadPostCommentsAction, LoadPostCommentsResult> { actions ->
            actions.flatMap {
                mainRepository.fetchCommentsForPost(it.postId)
                    .map { it.map(commentMapper::mapFromData) }
                    .map(LoadPostCommentsResult::Success)
                    .cast(LoadPostCommentsResult::class.java)
                    .onErrorReturn { LoadPostCommentsResult.Failure(it) }
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .startWith(LoadPostCommentsResult.Loading)
            }
        }


    internal val actionProcessor =
        ObservableTransformer<PostDetailsAction, PostDetailsResult> { actions ->
            actions.publish { shared ->
                Observable.merge(
                    shared.ofType(LoadPostCommentsAction::class.java).compose(loadPostCommentsProcessor),
                    shared.filter { action ->
                        action !is LoadPostCommentsAction
                    }.flatMap {  w ->
                        Observable.error<PostDetailsResult>(
                            IllegalArgumentException("Unknown Action Type: $w")
                        )
                    }
                )
            }
        }

}