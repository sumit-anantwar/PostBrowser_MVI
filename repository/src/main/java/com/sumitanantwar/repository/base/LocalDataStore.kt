package com.sumitanantwar.repository.base

import com.sumitanantwar.repository.model.PostData
import io.reactivex.Completable
import io.reactivex.Observable

interface LocalDataStore {

    fun clearFavorites(): Completable

    fun getFavoritePosts(): Observable<List<PostData>>

    fun setPostAsFavorite(postId: Int): Completable

    fun setPostAsNotFavorite(postId: Int): Completable
}