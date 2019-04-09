package com.sumitanantwar.presentation.mapper

import com.sumitanantwar.presentation.model.Post
import com.sumitanantwar.repository.model.PostData
import javax.inject.Inject

class PostMapper @Inject constructor() : Mapper<PostData, Post> {

    override fun mapFromData(data: PostData): Post {
        return Post(
            id      = data.id,
            userId  = data.userId,
            title   = data.title,
            body    = data.body
        )
    }
}