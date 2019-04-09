package com.sumitanantwar.presentation.postslist

import com.sumitanantwar.mvi.base.MviViewState
import com.sumitanantwar.presentation.model.Post
import com.sumitanantwar.repository.model.User

data class PostsListViewState(
    val isLoading: Boolean,
    val posts: List<Post>,
    val users: List<User>,
    val error: Throwable?
) : MviViewState {

    companion object {
        fun idle(): PostsListViewState = PostsListViewState(
            isLoading   = false,
            posts       = emptyList(),
            users       = emptyList(),
            error       = null
        )
    }
}