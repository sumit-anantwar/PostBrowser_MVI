package com.sumitanantwar.presentation.mapper

import com.sumitanantwar.network.testdata.TestDataFactory
import com.sumitanantwar.presentation.testdata.DataFactory
import com.sumitanantwar.repository.network.mapper.PostsModelMapper
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test

class UserMapperTest {

    private val testDataFactory = TestDataFactory()
    private val postModelMapper = PostsModelMapper()

    private val UserMapper_SUT = UserMapper()

    @Test
    fun test_UserMapper_Maps_USerDataToUser() {

        // GIVEN THAT
        val userData = DataFactory().makeUserData()

        // WHEN
        val user = UserMapper_SUT.mapFromData(userData)

        // THEN
        MatcherAssert.assertThat(user.id, CoreMatchers.`is`(userData.id))
        MatcherAssert.assertThat(user.username, CoreMatchers.`is`(userData.username))
        MatcherAssert.assertThat(user.name, CoreMatchers.`is`(userData.name))
        MatcherAssert.assertThat(user.email, CoreMatchers.`is`(userData.email))
    }
}