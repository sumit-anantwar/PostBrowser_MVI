package com.sumitanantwar.presentation.postslist

import com.sumitanantwar.mvi.base.MviIntent

sealed class PostsListIntent : MviIntent {
    object LoadAllUsersIntent : PostsListIntent()
    data class LoadPostsWithFilterIntent(val userId: String = "", val title: String = "", val body: String = "") : PostsListIntent()
}