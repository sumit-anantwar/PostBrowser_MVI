package com.sumitanantwar.network.testdata

import com.sumitanantwar.repository.network.model.CommentModel
import com.sumitanantwar.repository.network.model.PostModel
import com.sumitanantwar.repository.network.model.UserModel
import com.sumitanantwar.repository.network.service.NetworkService
import io.reactivex.Flowable
import io.reactivex.Observable

/** Network Service TestDouble */
class TestNetworkService : NetworkService {
    // Test Data
    private val testDataFactory = TestDataFactory()

    var fetchPostCount = 0
    var fetchPostWithFilterCount = 0

    var fetchUsersCount = 0
    var fetchUsersWithIdCount = 0

    var fetchCommentsForPostCount = 0

    override fun fetchPosts(): Observable<List<PostModel>> {
        fetchPostCount++
        return Observable.just(testDataFactory.getPostModelList())
    }

    override fun fetchPostsWithFilter(userId: Int): Observable<List<PostModel>> {
        fetchPostWithFilterCount++
        return Observable.just(testDataFactory.getFilteredPostModelList(userId))
    }


    override fun fetchUsers(): Observable<List<UserModel>> {
        fetchUsersCount++
        return Observable.just(testDataFactory.getUserModelList())
    }

    override fun fetchUserWithId(userId: Int): Observable<UserModel> {
        fetchUsersWithIdCount++
        return Observable.just(testDataFactory.getUserModelForId(userId))
    }

    override fun fetchCommentsForPost(postId: Int): Observable<List<CommentModel>> {
        fetchCommentsForPostCount++
        return Observable.just(testDataFactory.getCommentsForPostId(postId))
    }
}