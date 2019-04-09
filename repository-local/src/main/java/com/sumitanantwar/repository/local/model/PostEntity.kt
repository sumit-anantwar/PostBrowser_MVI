package com.sumitanantwar.repository.local.model


data class PostEntity (
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)