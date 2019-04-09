package com.sumitanantwar.repository.network.service


import com.sumitanantwar.repository.network.model.CommentModel
import com.sumitanantwar.repository.network.model.PostModel
import com.sumitanantwar.repository.network.model.UserModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkService {

    @GET("/posts")
    fun fetchPosts(): Observable<List<PostModel>>

    @GET("/posts")
    fun fetchPostsWithFilter(@Query("userId") userId: Int): Observable<List<PostModel>>

    @GET("/users")
    fun fetchUsers(): Observable<List<UserModel>>

    @GET("/users/{user}")
    fun fetchUserWithId(@Path("user") userId: Int): Observable<UserModel>

    @GET("/comments")
    fun fetchCommentsForPost(@Query("postId") postId: Int): Observable<List<CommentModel>>

}