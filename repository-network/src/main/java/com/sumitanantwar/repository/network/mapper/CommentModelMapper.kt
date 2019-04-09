package com.sumitanantwar.repository.network.mapper

import com.sumitanantwar.repository.model.CommentData
import com.sumitanantwar.repository.network.model.CommentModel
import javax.inject.Inject

class CommentModelMapper @Inject constructor() : ModelMapper<CommentModel, CommentData> {

    override fun mapFromModel(model: CommentModel): CommentData {
        return CommentData(
            model.id,
            model.postId,
            model.name,
            model.email,
            model.body
        )
    }
}