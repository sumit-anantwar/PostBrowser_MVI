package com.sumitanantwar.presentation.postdetails

import com.sumitanantwar.mvi.base.MviIntent
import com.sumitanantwar.presentation.model.Post

sealed class PostDetailsIntent : MviIntent {

    data class LoadPostCommentsIntent(val postId: Int) : PostDetailsIntent()

}