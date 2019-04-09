package com.sumitanantwar.network.mapper


import com.sumitanantwar.network.testdata.TestDataFactory
import com.sumitanantwar.repository.network.mapper.PostsModelMapper
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.hamcrest.CoreMatchers.`is` as _is

@RunWith(JUnit4::class)
class PostModelMapperTests {

    private val testDataFactory = TestDataFactory()

    // SUT
    private val PostsModelMapper_SUT = PostsModelMapper()


    @Test
    fun test_PostModelMapper_Maps_PostModel_To_Post() {

        // GIVEN THAT
        val postModel = testDataFactory.getRandomPostModel()

        // WHEN
        val postData = PostsModelMapper_SUT.mapFromModel(postModel)

        // THEN
        assertThat(postData.id,     _is(postModel.id))
        assertThat(postData.userId, _is(postModel.userId))
        assertThat(postData.title,  _is(postModel.title))
        assertThat(postData.body,   _is(postModel.body))
    }

}