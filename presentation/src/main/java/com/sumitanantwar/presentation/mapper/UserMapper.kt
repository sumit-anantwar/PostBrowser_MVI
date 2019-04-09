package com.sumitanantwar.presentation.mapper

import com.sumitanantwar.repository.model.User
import com.sumitanantwar.repository.model.UserData
import javax.inject.Inject

class UserMapper @Inject constructor() : Mapper<UserData, User> {
    override fun mapFromData(data: UserData): User {
        return User(
            id          = data.id,
            name        = data.name,
            username    = data.username,
            email       = data.email
        )
    }
}