package com.sumitanantwar.postsbrowser.mobile.application.di

import android.app.Application
import com.sumitanantwar.postsbrowser.mobile.application.MainApplication
import com.sumitanantwar.postsbrowser.mobile.application.di.scope.AppScope
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule


@AppScope
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ActivityModule::class,
        RepositoryModule::class,
        NetworkDataStoreModule::class,
        LocalDataStoreModule::class]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: MainApplication)

}