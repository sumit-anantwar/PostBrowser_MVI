package com.sumitanantwar.postsbrowser.mobile.application.di

import android.content.Context
import com.facebook.stetho.okhttp3.BuildConfig
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.sumitanantwar.postsbrowser.mobile.application.di.scope.AppScope
import com.sumitanantwar.repository.base.NetworkDataStore
import com.sumitanantwar.repository.network.NetworkDataStoreImpl
import com.sumitanantwar.repository.network.service.NetworkService
import com.sumitanantwar.repository.network.service.NetworkServiceFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File

@Module
abstract class NetworkDataStoreModule {

    @Module
    companion object {

        @Provides
        @AppScope
        @JvmStatic
        fun providesNetworkService(context: Context): NetworkService {

            val cacheDir = File(context.cacheDir, "okhttp-cache")
            val cache = Cache(cacheDir, 10 * 1000 * 1000)

            val okHttpClientBuilder = OkHttpClient.Builder()
                .addNetworkInterceptor(StethoInterceptor())
                .cache(cache)

            return NetworkServiceFactory.makeNetworkService(BuildConfig.DEBUG, okHttpClientBuilder)
        }
    }


    @Binds
    @AppScope
    abstract fun providesNetworkDataStore(networkDataStore: NetworkDataStoreImpl) : NetworkDataStore
}