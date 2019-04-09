package com.sumitanantwar.network.network

import com.sumitanantwar.repository.network.service.NetworkServiceFactory
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.hamcrest.CoreMatchers.`is` as _is


@RunWith(JUnit4::class)
class NetworkServiceFactoryTests {


    @Test
    fun test_BaseUrl_Returns_AppropriateUrl() {

        assertThat(NetworkServiceFactory.baseUrl(true), _is(NetworkServiceFactory.stagingUrl))
        assertThat(NetworkServiceFactory.baseUrl(false), _is(NetworkServiceFactory.productionUrl))


    }

}