package com.sumitanantwar.repository.base

import com.sumitanantwar.repository.model.CommentData
import com.sumitanantwar.repository.model.PostData
import com.sumitanantwar.repository.model.UserData
import io.reactivex.Flowable
import io.reactivex.Observable

interface NetworkDataStore {

    fun fetchAllPosts(): Observable<List<PostData>>
    fun fetchPostsWithFilter(userId: String, title: String, body: String): Observable<List<PostData>>
    fun fetchAllUsers(): Observable<List<UserData>>
    fun fetchAllCommentsForPost(postId: Int): Observable<List<CommentData>>

}