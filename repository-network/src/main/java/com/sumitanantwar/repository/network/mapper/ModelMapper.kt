package com.sumitanantwar.repository.network.mapper

interface ModelMapper<in M, out D> {
    fun mapFromModel(model: M) : D
}