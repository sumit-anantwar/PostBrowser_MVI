package com.sumitanantwar.repository.local.mapper

interface EntityMapper<E, D> {
    fun mapFromEntity(entity: E) : D
    fun mapToEntity(data: D) : E
}