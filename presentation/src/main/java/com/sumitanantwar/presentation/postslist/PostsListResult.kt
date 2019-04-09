package com.sumitanantwar.presentation.postslist

import com.sumitanantwar.presentation.model.Post
import com.sumitanantwar.repository.model.User

sealed class PostsListResult {

    sealed class LoadPostsResult : PostsListResult() {
        object Loading : LoadPostsResult()
        data class Success(val posts: List<Post>) : LoadPostsResult()
        data class Failure(val error: Throwable) : LoadPostsResult()
    }

    sealed class LoadAllUsersResult : PostsListResult() {
        object Loading : LoadAllUsersResult()
        data class Success(val users: List<User>) : LoadAllUsersResult()
        data class Failure(val error: Throwable) : LoadAllUsersResult()
    }

}