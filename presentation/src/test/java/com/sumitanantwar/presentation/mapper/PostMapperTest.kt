package com.sumitanantwar.presentation.mapper

import com.sumitanantwar.presentation.testdata.DataFactory
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.hamcrest.CoreMatchers.`is` as _is

class PostMapperTest {

    // SUT
    private val PostMapper_SUT = PostMapper()

    @Test
    fun test_PostMapper_Maps_PostDataToPost() {

        // GIVEN THAT
        val postData = DataFactory().makePostData()

        // WHEN
        val post = PostMapper_SUT.mapFromData(postData)

        // THEN
        assertThat(post.id,     _is(postData.id))
        assertThat(post.userId, _is(postData.userId))
        assertThat(post.title,  _is(postData.title))
        assertThat(post.body,   _is(postData.body))
    }


}