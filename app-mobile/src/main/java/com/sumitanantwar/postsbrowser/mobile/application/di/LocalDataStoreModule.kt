package com.sumitanantwar.postsbrowser.mobile.application.di


import com.sumitanantwar.postsbrowser.mobile.application.di.scope.AppScope
import com.sumitanantwar.repository.base.LocalDataStore
import com.sumitanantwar.repository.local.LocalDataStoreImpl
import dagger.Binds
import dagger.Module

@Module
abstract class LocalDataStoreModule {

    @Binds
    @AppScope
    abstract fun providesPosatsDataStore(postsDataStore: LocalDataStoreImpl): LocalDataStore
}