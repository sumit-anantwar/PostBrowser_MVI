package com.sumitanantwar.postsbrowser.mobile.testapp.di

import android.app.Application
import com.sumitanantwar.postsbrowser.mobile.application.di.ActivityModule
import com.sumitanantwar.postsbrowser.mobile.testapp.TestApp
import com.sumitanantwar.repository.base.MainRepository
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        TestAppModule::class,
        ActivityModule::class,
        TestRepositoryModule::class,
        TestDataStoreModule::class,
        TestNetworkDataStoreModule::class]
)
interface TestAppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): TestAppComponent.Builder

        fun build(): TestAppComponent
    }

    fun inject(app: TestApp)


    //======= Accessors for Testing =======
    // These will be resolved by Dagger using Static references due to the @JvmStatic annotation in the Modules
    fun postsRepository(): MainRepository

}