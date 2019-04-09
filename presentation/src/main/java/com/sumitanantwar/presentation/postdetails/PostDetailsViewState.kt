package com.sumitanantwar.presentation.postdetails

import com.sumitanantwar.mvi.base.MviViewState
import com.sumitanantwar.presentation.model.Comment

data class PostDetailsViewState(
    val isLoading: Boolean,
    val comments: List<Comment>,
    val error: Throwable?
) : MviViewState {

    companion object {
        fun idle(): PostDetailsViewState = PostDetailsViewState(
            isLoading = false,
            comments = emptyList(),
            error = null
        )
    }
}