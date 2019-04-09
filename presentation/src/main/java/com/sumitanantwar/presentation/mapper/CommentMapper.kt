package com.sumitanantwar.presentation.mapper

import com.sumitanantwar.presentation.model.Comment
import com.sumitanantwar.repository.model.CommentData
import javax.inject.Inject

class CommentMapper @Inject constructor() : Mapper<CommentData, Comment> {

    override fun mapFromData(data: CommentData): Comment {
        return Comment(
            id      = data.id,
            postId  = data.postId,
            name    = data.name,
            email   = data.email,
            body    = data.body
        )
    }

}