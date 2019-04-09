package com.sumitanantwar.repository.local

import com.sumitanantwar.repository.base.LocalDataStore
import com.sumitanantwar.repository.model.PostData
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class LocalDataStoreImpl @Inject constructor() : LocalDataStore {
    override fun clearFavorites(): Completable {
        return Completable.defer {
            // TODO: Not Implemented
            Completable.complete()
        }
    }

    override fun getFavoritePosts(): Observable<List<PostData>> {
        // TODO: Not Implemented
        return Observable.just(listOf(PostData(0, 0, "", "")))
    }

    override fun setPostAsFavorite(postId: Int): Completable {
        return Completable.defer {
            // TODO: Not Implemented
            Completable.complete()
        }
    }

    override fun setPostAsNotFavorite(postId: Int): Completable {
        return Completable.defer {
            // TODO: Not Implemented
            Completable.complete()
        }
    }
}