package com.sumitanantwar.presentation.postslist

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.sumitanantwar.network.testdata.TestNetworkService
import com.sumitanantwar.presentation.mapper.PostMapper
import com.sumitanantwar.presentation.mapper.UserMapper
import com.sumitanantwar.presentation.util.ImmediateSchedulerProvider
import com.sumitanantwar.repository.DataStoreFactory
import com.sumitanantwar.repository.MainRepositoryImpl
import com.sumitanantwar.repository.base.LocalDataStore
import com.sumitanantwar.repository.base.MainRepository
import com.sumitanantwar.repository.base.NetworkDataStore
import com.sumitanantwar.repository.local.LocalDataStoreImpl
import com.sumitanantwar.presentation.model.Post
import com.sumitanantwar.repository.network.NetworkDataStoreImpl
import com.sumitanantwar.repository.network.mapper.CommentModelMapper
import com.sumitanantwar.repository.network.mapper.PostsModelMapper
import com.sumitanantwar.repository.network.mapper.UserModelMapper
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test

class PostsListViewModelTests {

    // TestDoubles
    private lateinit var networkDataStore: NetworkDataStore
    private lateinit var localDataStore: LocalDataStore
    private lateinit var dataStoreFactory: DataStoreFactory
    private lateinit var mainRepository: MainRepository

    private val testNetworkService = TestNetworkService()
    private val immediateSchedulerProvider = ImmediateSchedulerProvider()

    // Helpers
    private lateinit var testObserver: TestObserver<PostsListViewState>

    // SUT
    private lateinit var PostListViewModel_SUT: PostsListViewModel

    @Before
    fun setup() {

        networkDataStore = NetworkDataStoreImpl(testNetworkService, PostsModelMapper(), UserModelMapper(), CommentModelMapper())
        localDataStore = LocalDataStoreImpl()
        dataStoreFactory = DataStoreFactory(localDataStore, networkDataStore)
        mainRepository = MainRepositoryImpl(dataStoreFactory)


        PostListViewModel_SUT =
            PostsListViewModel(
                PostsListProcessorHolder(
                    mainRepository,
                    immediateSchedulerProvider,
                    PostMapper(),
                    UserMapper()
                ))

        testObserver = PostListViewModel_SUT.states().test()
    }


    @Test
    fun test_FetchAllUsersFromNetwork_Loads_ListOfUsers() {

        // WHEN
        PostListViewModel_SUT.processIntents(Observable.just(PostsListIntent.LoadAllUsersIntent))

        // THEN
        testObserver.assertValueAt(1, PostsListViewState::isLoading)
        testObserver.assertValueAt(2) { !it.isLoading }
        testObserver.assertValueAt(2) {
            (!it.isLoading &&
                    it.posts.count() == 0 &&
                    it.users.count() == 10)
        }

    }


    @Test
    fun test_OnErrorFetchingUsers_Shows_Error() {

        // MOCK
        mainRepository = mock {
            on { fetchAllUsers() } doReturn Observable.error(Exception())
        }

        // GIVEN THAT
        PostListViewModel_SUT =
            PostsListViewModel(
                PostsListProcessorHolder(
                    mainRepository,
                    immediateSchedulerProvider,
                    PostMapper(),
                    UserMapper()
                ))


        testObserver = PostListViewModel_SUT.states().test()

        // WHEN
        PostListViewModel_SUT.processIntents(Observable.just(PostsListIntent.LoadAllUsersIntent))

        // THEN
        testObserver.assertValueAt(1, PostsListViewState::isLoading)
        testObserver.assertValueAt(2) { it.error != null }
    }


    @Test
    fun test_FetchPostsFilteredByUserId_Loads_FilteredListOfPosts() {

        // GIVEN THAT
        val expectedUserId = 2

        // WHEN
        PostListViewModel_SUT.processIntents(Observable.just(PostsListIntent.LoadPostsWithFilterIntent(userId = expectedUserId.toString())))

        // THEN
        testObserver
            .assertValueAt(1, PostsListViewState::isLoading)
            .assertValueAt(2) { !it.isLoading }
            .assertValueAt(2) { (!it.isLoading && it.posts.count() == 10) }
            .assertValueAt(2) { it.posts.first().userId == expectedUserId }
            .assertValueAt(2) { it.posts.random().userId == expectedUserId }
            .assertValueAt(2) { it.posts.last().userId == expectedUserId }
    }

    @Test
    fun test_FetchPostsFilteredByUserIdAndTitle_Returns_FilteredListOfPosts() {

        runCommonTesterForFilter(userId = "2", title = "et")
    }

    @Test
    fun test_FetchPostsFilteredByUserIdAndBody_Returns_FilteredListOfPosts() {

        runCommonTesterForFilter(userId = "4", body = "sed")

    }

    @Test
    fun test_FetchPostsFilteredByBody_Returns_FilteredListOfPosts() {

        runCommonTesterForFilter(body = "sed")

    }

    @Test
    fun test_FetchPostsFilteredByTitle_Returns_FilteredListOfPosts() {

        runCommonTesterForFilter(title = "est")

    }


    //======= Test Helper =======
    private fun runCommonTesterForFilter(userId: String = "", title: String = "", body: String = "") {

        // Internal function to assert valid post
        fun Post.assertValid() : Boolean {

            var isvalid = this.title.contains(title) && this.body.contains(body)

            val uid = userId.toIntOrNull()
            if (uid != null) {
                isvalid = isvalid && (this.userId == uid)
            }

            return isvalid
        }

        // WHEN
        PostListViewModel_SUT.processIntents(
            Observable.just(
                PostsListIntent.LoadPostsWithFilterIntent(
                    userId = userId,
                    title = title,
                    body = body)))

        // THEN
        testObserver
            .assertValueAt(1, PostsListViewState::isLoading)
            .assertValueAt(2) { !it.isLoading }
            .assertValueAt(2) { it.posts.first().assertValid() }
            .assertValueAt(2) { it.posts.random().assertValid() }
            .assertValueAt(2) { it.posts.last().assertValid() }

    }
}