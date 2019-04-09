package com.sumitanantwar.presentation.postslist

import com.sumitanantwar.network.testdata.TestNetworkService
import com.sumitanantwar.presentation.mapper.CommentMapper
import com.sumitanantwar.presentation.mapper.PostMapper
import com.sumitanantwar.presentation.mapper.UserMapper
import com.sumitanantwar.presentation.postdetails.PostDetailsIntent
import com.sumitanantwar.presentation.postdetails.PostDetailsProcessorHolder
import com.sumitanantwar.presentation.postdetails.PostDetailsViewModel
import com.sumitanantwar.presentation.postdetails.PostDetailsViewState
import com.sumitanantwar.presentation.util.ImmediateSchedulerProvider
import com.sumitanantwar.repository.DataStoreFactory
import com.sumitanantwar.repository.MainRepositoryImpl
import com.sumitanantwar.repository.base.LocalDataStore
import com.sumitanantwar.repository.base.MainRepository
import com.sumitanantwar.repository.base.NetworkDataStore
import com.sumitanantwar.repository.local.LocalDataStoreImpl
import com.sumitanantwar.repository.network.NetworkDataStoreImpl
import com.sumitanantwar.repository.network.mapper.CommentModelMapper
import com.sumitanantwar.repository.network.mapper.PostsModelMapper
import com.sumitanantwar.repository.network.mapper.UserModelMapper
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test

class PostDetailsViewModelTests {

    // TestDoubles
    private lateinit var networkDataStore: NetworkDataStore
    private lateinit var localDataStore: LocalDataStore
    private lateinit var dataStoreFactory: DataStoreFactory
    private lateinit var mainRepository: MainRepository

    private val testNetworkService = TestNetworkService()
    private val immediateSchedulerProvider = ImmediateSchedulerProvider()

    // Helpers
    private lateinit var testObserver: TestObserver<PostDetailsViewState>

    // SUT
    private lateinit var PostDetailsViewModel_SUT: PostDetailsViewModel

    @Before
    fun setup() {

        networkDataStore = NetworkDataStoreImpl(testNetworkService, PostsModelMapper(), UserModelMapper(), CommentModelMapper())
        localDataStore = LocalDataStoreImpl()
        dataStoreFactory = DataStoreFactory(localDataStore, networkDataStore)
        mainRepository = MainRepositoryImpl(dataStoreFactory)


        PostDetailsViewModel_SUT =
            PostDetailsViewModel(
                PostDetailsProcessorHolder(
                    mainRepository,
                    immediateSchedulerProvider,
                    CommentMapper()
                )
            )

        testObserver = PostDetailsViewModel_SUT.states().test()
    }


    @Test
    fun test_FetchPostCommentsFromNetwork_Loads_ListOfComments() {

        // GIVEN THAT
        val intent = PostDetailsIntent.LoadPostCommentsIntent(2)

        // WHEN
        PostDetailsViewModel_SUT.processIntents(Observable.just(intent))

        // THEN
        testObserver
            .assertValueAt(1, PostDetailsViewState::isLoading)
            .assertValueAt(2) { !it.isLoading }
            .assertValueAt(2) {
                (!it.isLoading &&
                        it.comments.count() == 5)
            }

    }

}