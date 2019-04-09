package com.sumitanantwar.repository.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: Int,
    val name: String,
    val username: String,
    val email: String
) : Parcelable {

    val profileImageUrl = "https://api.adorable.io/avatars/101/$email"
}