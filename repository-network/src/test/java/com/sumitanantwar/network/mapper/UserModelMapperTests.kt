package com.sumitanantwar.network.mapper

import com.sumitanantwar.network.testdata.TestDataFactory
import com.sumitanantwar.repository.network.mapper.UserModelMapper
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.hamcrest.CoreMatchers.`is` as _is

@RunWith(JUnit4::class)
class UserModelMapperTests {

    private val testDataFactory = TestDataFactory()

    // SUT
    private val UserModelMapper_SUT = UserModelMapper()


    @Test
    fun test_UserModelMapper_Maps_UserModel_To_User() {

        // GIVEN THAT
        val userModel = testDataFactory.getRandomUserModel()

        // WHEN
        val user = UserModelMapper_SUT.mapFromModel(userModel)

        // THEN
        assertThat(user.id,         _is(userModel.id))
        assertThat(user.username,   _is(userModel.username))
        assertThat(user.email,      _is(userModel.email))
        assertThat(user.name,       _is(userModel.name))
    }
}