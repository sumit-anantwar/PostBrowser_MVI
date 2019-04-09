package com.sumitanantwar.presentation.postdetails

import com.sumitanantwar.mvi.base.MviAction
import com.sumitanantwar.presentation.model.Post

sealed class PostDetailsAction : MviAction {

    data class LoadPostCommentsAction(val postId: Int) : PostDetailsAction()

}