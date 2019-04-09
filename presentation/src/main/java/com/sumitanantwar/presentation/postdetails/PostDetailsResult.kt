package com.sumitanantwar.presentation.postdetails

import com.sumitanantwar.presentation.model.Comment

sealed class PostDetailsResult {

    sealed class LoadPostCommentsResult : PostDetailsResult() {
        object Loading : LoadPostCommentsResult()
        data class Success(val comments: List<Comment>) : LoadPostCommentsResult()
        data class Failure(val error: Throwable) : LoadPostCommentsResult()
    }
}