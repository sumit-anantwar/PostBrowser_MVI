package com.sumitanantwar.network.network

import com.sumitanantwar.network.testdata.TestNetworkService
import com.sumitanantwar.repository.model.PostData
import com.sumitanantwar.repository.network.NetworkDataStoreImpl
import com.sumitanantwar.repository.network.mapper.CommentModelMapper
import com.sumitanantwar.repository.network.mapper.PostsModelMapper
import com.sumitanantwar.repository.network.mapper.UserModelMapper
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.hamcrest.CoreMatchers.`is` as _is

@RunWith(JUnit4::class)
class NetworkDataStoreImplTests {


    private val postModelMapper     = PostsModelMapper()
    private val userModelMapper     = UserModelMapper()
    private val commentModelMapper  = CommentModelMapper()
    private val testNetworkService  = TestNetworkService()

    // SUT
    private lateinit var NetworkDataStoreImpl_SUT: NetworkDataStoreImpl

    @Before
    fun setup() {

        NetworkDataStoreImpl_SUT = NetworkDataStoreImpl(testNetworkService, postModelMapper, userModelMapper, commentModelMapper)
    }

    @After
    fun teardown() {

    }

    @Test
    fun test_FetchPosts_Returns_ListOfPosts() {

        //=== WHEN ===
        val postsFlowable = NetworkDataStoreImpl_SUT.fetchAllPosts()

        //=== THEN ===

        // Verify that appropriate API endpoint is being called
        assertThat(testNetworkService.fetchPostCount, _is(1))

        // Verify the returned results
        postsFlowable.test()
            .assertSubscribed()
            .assertValue {
                it.count() == 100
            }
            .assertValue {
                it.first().id == 1
            }
            .assertValue {
                it.last().id == 100
            }
            .assertComplete()
            .assertNoErrors()

    }

    @Test
    fun test_FetchPostsWithFilter_Returns_FilteredListOfPosts_1() {
        testFilteredPostFetch("2")
    }

    @Test
    fun test_FetchPostsWithFilter_Returns_FilteredListOfPosts_2() {
        testFilteredPostFetch("3", "est")
    }

    @Test
    fun test_FetchPostsWithFilter_Returns_FilteredListOfPosts_3() {
        testFilteredPostFetch("4", body = "sed")
    }

    @Test
    fun test_FetchPostsWithFilter_Returns_FilteredListOfPosts_4() {
        testFilteredPostFetch(body = "sed")
    }

    private fun testFilteredPostFetch(userId: String = "", title: String = "", body: String = "") {

        // Internal function to assert a valid Post
        fun PostData.assertValid(): Boolean {
            val uid = userId.toIntOrNull()
            var isValid = false
            if (uid != null) {
                isValid = (this.userId == uid)
            }
            isValid = this.title.contains(title) && this.body.contains(body)

            return isValid
        }

        //=== WHEN ===
        val postsObservable = NetworkDataStoreImpl_SUT.fetchPostsWithFilter(userId, title, body)

        //=== THEN ===

        // Verify that appropriate API endpoint is being called
        val uid = userId.toIntOrNull()
        if (uid != null) {
            assertThat(testNetworkService.fetchPostWithFilterCount, _is(1))
        } else {
            assertThat(testNetworkService.fetchPostCount, _is(1))
        }

        // Verify the returned results
        postsObservable.test()
            .assertSubscribed()
            .assertValue {
                it.first().assertValid()
            }
            .assertValue {
                it.random().assertValid()
            }
            .assertValue {
                it.last().assertValid()
            }
            .assertComplete()
            .assertNoErrors()
    }

    @Test
    fun test_FetchUsers_Returns_ListOfUsers() {

        //=== WHEN ===
        val usersObservable = NetworkDataStoreImpl_SUT.fetchAllUsers()

        //=== THEN ===

        // Verify that appropriate API endpoint is being called
        assertThat(testNetworkService.fetchUsersCount, _is(1))

        // Verify the returned results
        usersObservable.test()
            .assertSubscribed()
            .assertValue {
                it.count() == 10
            }
            .assertValue {
                it.first().id == 1
            }
            .assertValue {
                it.last().id == 10
            }
            .assertComplete()
            .assertNoErrors()
    }

    @Test
    fun test_FetchCommentsForPost_Returns_ListOfComments() {

        // GIVEN THAT
        val expectedPostId = 3

        //=== WHEN ===
        val usersObservable = NetworkDataStoreImpl_SUT.fetchAllCommentsForPost(expectedPostId)

        //=== THEN ===

        // Verify that appropriate API endpoint is being called
        assertThat(testNetworkService.fetchCommentsForPostCount, _is(1))

        // Verify the returned results
        usersObservable.test()
            .assertSubscribed()
            .assertValue {
                it.random().postId == expectedPostId
            }
            .assertValue {
                it.first().postId == expectedPostId
            }
            .assertValue {
                it.last().postId == expectedPostId
            }
            .assertComplete()
            .assertNoErrors()
    }


}