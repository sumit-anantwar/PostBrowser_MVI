package com.sumitanantwar.presentation.mapper

interface Mapper<in D, out V> {
    fun mapFromData(data: D) : V
}