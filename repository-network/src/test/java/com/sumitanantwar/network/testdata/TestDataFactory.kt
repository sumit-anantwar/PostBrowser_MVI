package com.sumitanantwar.network.testdata

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.sumitanantwar.repository.network.model.CommentModel
import com.sumitanantwar.repository.network.model.PostModel
import com.sumitanantwar.repository.network.model.UserModel
import java.nio.charset.Charset

class TestDataFactory {


    private val postModelsList: List<PostModel> by lazy {
        return@lazy loadJsonToArray<PostModel>("posts.json")
    }

    private val userModelsList: List<UserModel> by lazy {
        return@lazy loadJsonToArray<UserModel>("users.json")
    }

    private val commentModelsList: List<CommentModel> by lazy {
        return@lazy loadJsonToArray<CommentModel>("comments.json")
    }


    private inline fun <reified T> loadJsonToArray(filename: String) : List<T> {
        val classLoader = javaClass.classLoader
        val resource = classLoader.getResource(filename)

        val inputStream = resource.openStream()
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()

        val jsonString = String(buffer, Charset.defaultCharset())

        val moshi = Moshi.Builder().build()
        val listType = Types.newParameterizedType(List::class.java, T::class.java)
        val adapter: JsonAdapter<List<T>> = moshi.adapter(listType)

        return adapter.fromJson(jsonString)!!
    }

    //======= Posts =======
    fun getPostModelList(): List<PostModel> = postModelsList

    fun getFilteredPostModelList(userId: Int): List<PostModel> {

        val filteredList = postModelsList.filter { post ->
            (post.userId == userId)
        }

        return filteredList
    }

    fun getRandomPostModel(): PostModel {

        return postModelsList.random()
    }

    //======= Users =======
    fun getUserModelList(): List<UserModel> = userModelsList

    fun getUserModelForId(userId: Int): UserModel? {
        return userModelsList.firstOrNull { it.id == userId }
    }

    fun getRandomUserModel(): UserModel {
        return userModelsList.random()
    }

    //======= Comments =======
    fun getCommentModelList(): List<CommentModel> = commentModelsList

    fun getCommentsForPostId(postId: Int) : List<CommentModel> {
        return commentModelsList.filter { it.postId == postId }
    }

    fun getCommentModelForId(commentId: Int): CommentModel? {
        return commentModelsList.firstOrNull { it.id == commentId }
    }

    fun getRandomCommentModel(): CommentModel {
        return commentModelsList.random()
    }
}