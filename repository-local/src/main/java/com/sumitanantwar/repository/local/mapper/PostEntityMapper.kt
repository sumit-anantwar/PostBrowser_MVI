package com.sumitanantwar.repository.local.mapper

import com.sumitanantwar.repository.local.model.PostEntity
import com.sumitanantwar.repository.model.PostData

class PostEntityMapper : EntityMapper<PostEntity, PostData> {

    override fun mapFromEntity(entity: PostEntity): PostData {
        return PostData(entity.id, entity.userId, entity.title, entity.body)
    }

    override fun mapToEntity(data: PostData): PostEntity {
        return PostEntity(data.id, data.userId, data.title, data.body)
    }

}