package com.sumitanantwar.postsbrowser.mobile.testapp.di

import com.sumitanantwar.postsbrowser.mobile.scheduler.RegularSchedulerProvider
import com.sumitanantwar.repository.DataStoreFactory
import com.sumitanantwar.repository.MainRepositoryImpl
import com.sumitanantwar.repository.base.MainRepository
import com.sumitanantwar.repository.scheduler.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object TestRepositoryModule {

    @Provides
    @JvmStatic
    fun bindsUiSchedulerProvider(): SchedulerProvider {
        return RegularSchedulerProvider()
    }

    @Provides
    @JvmStatic
    @Singleton
    fun providesPostsRepository(dataStoreFactory: DataStoreFactory): MainRepository {
        return MainRepositoryImpl(dataStoreFactory)
    }

}