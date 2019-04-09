package com.sumitanantwar.repository.local.model

data class CommentEntity(
    val id: Int,
    val postId: Int,
    val name: String,
    val email: String,
    val body: String
)