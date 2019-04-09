package com.sumitanantwar.network.mapper

import com.sumitanantwar.network.testdata.TestDataFactory
import com.sumitanantwar.repository.network.mapper.CommentModelMapper
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.hamcrest.CoreMatchers.`is` as _is

@RunWith(JUnit4::class)
class CommentModelMapperTests {

    private val testDataFactory = TestDataFactory()

    // SUT
    private val CommentModelMapper_SUT = CommentModelMapper()


    @Test
    fun test_CommentModelMapper_Maps_CommentModel_To_Comment() {

        // GIVEN THAT
        val commentModel = testDataFactory.getRandomCommentModel()

        // WHEN
        val comment = CommentModelMapper_SUT.mapFromModel(commentModel)

        // THEN
        assertThat(comment.id, _is(commentModel.id))
        assertThat(comment.postId, _is(commentModel.postId))
        assertThat(comment.name, _is(commentModel.name))
        assertThat(comment.email, _is(commentModel.email))
        assertThat(comment.body, _is(commentModel.body))

    }

}