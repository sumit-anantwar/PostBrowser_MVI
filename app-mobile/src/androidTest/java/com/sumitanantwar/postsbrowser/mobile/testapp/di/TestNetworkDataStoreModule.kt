package com.sumitanantwar.postsbrowser.mobile.testapp.di

import com.sumitanantwar.network.testdata.TestNetworkService
import com.sumitanantwar.repository.base.NetworkDataStore
import com.sumitanantwar.repository.network.NetworkDataStoreImpl
import com.sumitanantwar.repository.network.mapper.CommentModelMapper
import com.sumitanantwar.repository.network.mapper.PostsModelMapper
import com.sumitanantwar.repository.network.mapper.UserModelMapper
import com.sumitanantwar.repository.network.service.NetworkService
import dagger.Module
import dagger.Provides

@Module
object TestNetworkDataStoreModule {

    @Provides
    @JvmStatic
    fun providesNetworkService(): NetworkService {
        return TestNetworkService()
    }

    @Provides
    @JvmStatic
    fun providesNetworkDataStore(networkService: NetworkService): NetworkDataStore {
        return NetworkDataStoreImpl(networkService, PostsModelMapper(), UserModelMapper(), CommentModelMapper())
    }

}