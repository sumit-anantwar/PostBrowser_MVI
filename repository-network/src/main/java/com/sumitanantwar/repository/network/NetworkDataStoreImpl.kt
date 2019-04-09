package com.sumitanantwar.repository.network;

import com.sumitanantwar.repository.base.NetworkDataStore
import com.sumitanantwar.repository.model.CommentData
import com.sumitanantwar.repository.model.PostData
import com.sumitanantwar.repository.model.UserData
import com.sumitanantwar.repository.network.mapper.CommentModelMapper
import com.sumitanantwar.repository.network.mapper.PostsModelMapper
import com.sumitanantwar.repository.network.mapper.UserModelMapper
import com.sumitanantwar.repository.network.service.NetworkService
import io.reactivex.Observable
import javax.inject.Inject


class NetworkDataStoreImpl @Inject constructor(
    private val networkService: NetworkService,
    private val postModelMapper: PostsModelMapper,
    private val userModelMapper: UserModelMapper,
    private val commentModelMapper: CommentModelMapper
) : NetworkDataStore {

    /** Fetches all Posts */
    override fun fetchAllPosts(): Observable<List<PostData>> {
        return networkService.fetchPosts()
            .map {
                it.map {
                    postModelMapper.mapFromModel(it)
                }
            }
    }

    /**
     * Fetches posts filtered by [userId], [title], [body]
     *
     * Issue: JSONPlaceholder does not provide filtering APIs.
     * Although it provides API which can query post with title or body,
     * it requires that the "whole" title or body be used therein.
     *
     * But querying posts using userId works as expected.
     *
     * Hence, we will use the API to fetch posts for the specified userId,
     * and then filter them locally using the supplied title and body strings.
     */
    override fun fetchPostsWithFilter(userId: String, title: String, body: String): Observable<List<PostData>> {

        // Internal function to apply filter
        fun Observable<List<PostData>>.applyFilter(): Observable<List<PostData>> {
            return this.map {
                it.filter {
                    it.title.contains(title) &&
                            it.body.contains(body)
                }
            }
        }

        // If the userId is null, fetch all the posts and then apply the filter
        val uid = userId.toIntOrNull()
        if (uid == null) {
            return fetchAllPosts().applyFilter()
        }
        else {
            return fetchPostsWithUserId(uid).applyFilter()
        }
    }

    /** Fetches all posts for [userId] */
    private fun fetchPostsWithUserId(userId: Int): Observable<List<PostData>> {
        return networkService.fetchPostsWithFilter(userId)
            .map {
                it.map {
                    postModelMapper.mapFromModel(it)
                }
            }
    }

    /** Fetch all the users */
    override fun fetchAllUsers(): Observable<List<UserData>> {
        return networkService.fetchUsers()
            .map { userModelList ->
                userModelList.map {  userModel ->
                    userModelMapper.mapFromModel(userModel)
                }
            }
    }

    override fun fetchAllCommentsForPost(postId: Int): Observable<List<CommentData>> {
        return networkService.fetchCommentsForPost(postId)
            .map { commentModelList ->
                commentModelList.map {commentModel ->
                    commentModelMapper.mapFromModel(commentModel)
                }
            }
    }



}
