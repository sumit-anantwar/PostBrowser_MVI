package com.sumitanantwar.presentation.postslist

import com.sumitanantwar.mvi.base.MviAction

sealed class PostsListAction : MviAction {
    object LoadAllUsersAction : PostsListAction()
    data class LoadPostsWithFilterAction(val userId: String, val title: String, val body: String) : PostsListAction()
}