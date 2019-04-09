package com.sumitanantwar.presentation.mapper

import com.sumitanantwar.presentation.testdata.DataFactory
import org.hamcrest.CoreMatchers.`is` as _is
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class CommentMapperTest {

    // SUT
    private val CommentMapper_SUT = CommentMapper()

    @Test
    fun test_PostMapper_Maps_PostDataToPost() {

        // GIVEN THAT
        val commentData = DataFactory().makeCommentData()

        // WHEN
        val comment = CommentMapper_SUT.mapFromData(commentData)

        // THEN
        assertThat(comment.id, _is(commentData.id))
        assertThat(comment.postId, _is(commentData.postId))
        assertThat(comment.name, _is(commentData.name))
        assertThat(comment.email, _is(commentData.email))
        assertThat(comment.body, _is(commentData.body))
    }
}