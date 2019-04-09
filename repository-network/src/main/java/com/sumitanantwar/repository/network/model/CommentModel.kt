package com.sumitanantwar.repository.network.model

/*
{
  "postId": 1,
  "id": 1,
  "name": "id labore ex et quam laborum",
  "email": "Eliseo@gardner.biz",
  "body": "laudantium enim quasi est quidem magnam voluptate ipsam eos\ntempora quo necessitatibus\ndolor quam autem quasi\nreiciendis et nam sapiente accusantium"
}
 */

data class CommentModel(
    val id: Int,
    val postId: Int,
    val name: String,
    val email: String,
    val body: String
)
