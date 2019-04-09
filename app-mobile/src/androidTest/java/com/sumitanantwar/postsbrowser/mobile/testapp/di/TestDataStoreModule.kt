package com.sumitanantwar.postsbrowser.mobile.testapp.di

import com.nhaarman.mockitokotlin2.mock
import com.sumitanantwar.repository.base.LocalDataStore
import dagger.Module
import dagger.Provides

@Module
object TestDataStoreModule {

    @Provides
    @JvmStatic
    fun providesPosatsDataStore(): LocalDataStore {
        return mock()
    }

}