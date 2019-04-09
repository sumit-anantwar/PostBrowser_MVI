package com.sumitanantwar.presentation.testdata

import com.sumitanantwar.repository.model.CommentData
import com.sumitanantwar.repository.model.PostData
import com.sumitanantwar.repository.model.UserData
import kotlin.random.Random

class DataFactory {

    fun randomInt() : Int {
        return Random.nextInt(100)
    }

    fun randomString() : String {
        return java.util.UUID.randomUUID().toString()
    }


    fun makePostData() : PostData {
        return PostData(randomInt(), randomInt(), randomString(), randomString())
    }

    fun makeUserData() : UserData {
        return UserData(randomInt(), randomString(), randomString(), randomString())
    }

    fun makeCommentData() : CommentData {
        return CommentData(randomInt(), randomInt(), randomString(), randomString(), randomString())
    }
}