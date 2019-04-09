package com.sumitanantwar.repository.network.mapper

import com.sumitanantwar.repository.model.PostData
import com.sumitanantwar.repository.network.model.PostModel
import javax.inject.Inject

class PostsModelMapper @Inject constructor() : ModelMapper<PostModel, PostData> {

    override fun mapFromModel(model: PostModel): PostData {
        return PostData(
            model.id,
            model.userId,
            model.title,
            model.body
        )
    }
}