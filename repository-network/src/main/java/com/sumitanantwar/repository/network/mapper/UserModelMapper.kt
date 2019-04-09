package com.sumitanantwar.repository.network.mapper

import com.sumitanantwar.repository.model.UserData
import com.sumitanantwar.repository.network.model.UserModel
import javax.inject.Inject

class UserModelMapper @Inject constructor() : ModelMapper<UserModel, UserData> {

    override fun mapFromModel(model: UserModel): UserData {
        return UserData(
            model.id,
            model.name,
            model.username,
            model.email
        )
    }
}