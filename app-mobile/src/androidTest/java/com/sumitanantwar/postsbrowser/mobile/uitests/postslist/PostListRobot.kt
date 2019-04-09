package com.sumitanantwar.postsbrowser.mobile.uitests.postslist

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.sumitanantwar.postsbrowser.mobile.R
import com.sumitanantwar.postsbrowser.mobile.helpers.hasItemAtPosition
import com.sumitanantwar.postsbrowser.mobile.ui.main.MainActivity
import com.sumitanantwar.postsbrowser.mobile.uitests.BaseRobot
import org.hamcrest.CoreMatchers.not

fun withPostsListBrowserRobot(activityTestRule: ActivityTestRule<MainActivity>, func: PostListBrowserRobot.() -> Unit) = PostListBrowserRobot(activityTestRule).apply { func() }

class PostListBrowserRobot(activityTestRule: ActivityTestRule<MainActivity>) : BaseRobot<MainActivity>(activityTestRule) {

    fun checkRecyclerIsDisplayed() {
        onView(
            withId(R.id.recycler_posts)
        ).check(
            matches(isDisplayed())
        )
    }

    fun checkFilterLayoutIsHidden() {
        onView(
            withId(R.id.layout_filter)
        ).check(
            matches(not(isDisplayed()))
        )
    }

    fun checkFilterLayoutIsDisplayed() {
        onView(
            withId(R.id.layout_filter)
        ).check(
            matches(isDisplayed())
        )
    }

    fun clickOnFilterButton() {
        onView(
            withId(R.id.button_filter)
        ).check(
            matches(isDisplayed())
        ).perform(
            click()
        )
    }

    fun scrollRecyclerViewToPosition(position: Int) {
        onView(
            withId(R.id.recycler_posts)
        ).perform(
            scrollToPosition<androidx.recyclerview.widget.RecyclerView.ViewHolder>(position)
        )
    }

    fun typeInUserIdFilter(filter: String) {
        editText_Type(R.id.edit_text_userid, filter)
    }

    fun clearUserIdFilter() {
        editText_Replace(R.id.edit_text_userid, "")
    }

    fun typeInTitleFilter(filter: String) {
        editText_Type(R.id.edit_text_title, filter)
    }

    fun clearTitleFilter() {
        editText_Replace(R.id.edit_text_title, "")
    }

    fun checkRecyclerViewAtPositionHasUserId(position: Int, userIdFilter: Int) {
        scrollRecyclerViewToPosition(position)

        onView(
            withId(R.id.recycler_posts)
        ).check(
            matches(hasItemAtPosition(position, withText(userIdFilter.toString()), R.id.text_userid))
        )
    }

    fun checkRecyclerViewAtPositionContainsStringInTitle(position: Int, titleFilter: String) {
        scrollRecyclerViewToPosition(position)

        onView(
            withId(R.id.recycler_posts)
        ).check(
            matches(hasItemAtPosition(position, withSubstring(titleFilter.toString()), R.id.text_post_title))
        )
    }

    fun clickRecyclerViewAtPosition(position: Int)  {
        scrollRecyclerViewToPosition(position)

        onView(
            withId(R.id.recycler_posts)
        ).perform(
            click()
        )
    }
}