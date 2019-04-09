package com.sumitanantwar.postsbrowser.mobile.application.di

import com.sumitanantwar.postsbrowser.mobile.application.di.scope.AppScope
import com.sumitanantwar.postsbrowser.mobile.scheduler.RegularSchedulerProvider
import com.sumitanantwar.repository.MainRepositoryImpl
import com.sumitanantwar.repository.base.MainRepository
import com.sumitanantwar.repository.scheduler.SchedulerProvider
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    @AppScope
    abstract fun bindsUiSchedulerProvider(regularSchedulerProvider: RegularSchedulerProvider) : SchedulerProvider

    @Binds
    @AppScope
    abstract fun bindsMainRepository(mainRepository: MainRepositoryImpl) : MainRepository
}