package com.sumitanantwar.repository

import com.sumitanantwar.repository.base.MainRepository
import com.sumitanantwar.repository.model.CommentData
import com.sumitanantwar.repository.model.PostData
import com.sumitanantwar.repository.model.UserData
import io.reactivex.Observable
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val dataStoreFactory: DataStoreFactory
) : MainRepository {


    override fun fetchAllPosts(): Observable<List<PostData>> {

        return dataStoreFactory.getNetworkDataStore()
            .fetchAllPosts()
            .distinctUntilChanged()
    }

    /**
     * Fetch posts filtered using [userId], [title] and [body]
     */
    override fun fetchPostsWithFilter(userId: String, title: String, body: String): Observable<List<PostData>> {

        return dataStoreFactory.getNetworkDataStore()
            .fetchPostsWithFilter(userId, title, body)
            .distinctUntilChanged()
    }


    override fun fetchAllUsers(): Observable<List<UserData>> {

        return dataStoreFactory.getNetworkDataStore()
            .fetchAllUsers()
            .distinctUntilChanged()
    }

    override fun fetchCommentsForPost(postId: Int): Observable<List<CommentData>> {

        return dataStoreFactory.getNetworkDataStore()
            .fetchAllCommentsForPost(postId)
            .distinctUntilChanged()
    }

    override fun getFavoritePosts(): Observable<List<PostData>> {

        return dataStoreFactory.getLocalDataStore()
            .getFavoritePosts()

    }


}