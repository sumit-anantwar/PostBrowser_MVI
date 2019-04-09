package com.sumitanantwar.presentation.postslist

import com.sumitanantwar.presentation.mapper.PostMapper
import com.sumitanantwar.presentation.mapper.UserMapper
import com.sumitanantwar.presentation.postslist.PostsListAction.*
import com.sumitanantwar.presentation.postslist.PostsListResult.LoadAllUsersResult
import com.sumitanantwar.presentation.postslist.PostsListResult.LoadPostsResult
import com.sumitanantwar.repository.base.MainRepository
import com.sumitanantwar.repository.scheduler.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class PostsListProcessorHolder @Inject constructor(
    private val mainRepository: MainRepository,
    private val schedulerProvider: SchedulerProvider,
    private val postMapper: PostMapper,
    private val userMapper: UserMapper
) {

    private val loadPostsWithFilterProcessor =
        ObservableTransformer<LoadPostsWithFilterAction, LoadPostsResult> { actions ->
            actions.flatMap {
                mainRepository.fetchPostsWithFilter(it.userId, it.title, it.body)
                    .map{ it.map(postMapper::mapFromData) }
                    .map(LoadPostsResult::Success)
                    .cast(LoadPostsResult::class.java)
                    .onErrorReturn { LoadPostsResult.Failure(it) }
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .startWith(LoadPostsResult.Loading)
            }
        }

    private val loadUsersProcessor =
        ObservableTransformer<LoadAllUsersAction, LoadAllUsersResult> { actions ->
            actions.flatMap {
                mainRepository.fetchAllUsers()
                    .map { it.map(userMapper::mapFromData) }
                    .map(LoadAllUsersResult::Success)
                    .cast(LoadAllUsersResult::class.java)
                    .onErrorReturn {
                        LoadAllUsersResult.Failure(it)
                    }
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .startWith(LoadAllUsersResult.Loading)
            }
        }


    /**
     * Converts [PostsListAction] to [PostsListResult]
     *
     * Emits Error if wrong action type is passed in
     */
    internal val actionProcessor =
        ObservableTransformer<PostsListAction, PostsListResult> { actions ->
            actions.publish { shared ->
                Observable.merge(
                    shared.ofType(LoadPostsWithFilterAction::class.java).compose(loadPostsWithFilterProcessor),
                    shared.ofType(LoadAllUsersAction::class.java).compose(loadUsersProcessor))
                    .mergeWith(
                        shared.filter { action ->
                            action !is LoadPostsWithFilterAction &&
                                    action !is LoadAllUsersAction
                        }.flatMap { w ->
                            Observable.error<PostsListResult>(
                                IllegalArgumentException("Unknown Action Type: $w")
                            )
                        }
                    )
            }
        }

}