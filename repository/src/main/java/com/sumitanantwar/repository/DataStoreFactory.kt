package com.sumitanantwar.repository

import com.sumitanantwar.repository.base.LocalDataStore
import com.sumitanantwar.repository.base.NetworkDataStore
import javax.inject.Inject

class DataStoreFactory @Inject constructor(
    private val localDataStore: LocalDataStore,
    private val networkDataStore: NetworkDataStore
) {

    fun getLocalDataStore(): LocalDataStore {
        return localDataStore
    }

    fun getNetworkDataStore(): NetworkDataStore {
        return networkDataStore
    }
}