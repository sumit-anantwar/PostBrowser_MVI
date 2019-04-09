package com.sumitanantwar.postsbrowser.mobile.uitests

import android.app.Activity
import android.content.Intent
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Matchers

/** Makes a non-launching [ActivityTestRule]
 * so that we can stub network responses before launching the activity  */
inline fun <reified T : Activity> makeActivityTestRule(): ActivityTestRule<T> {
    return ActivityTestRule(T::class.java, false, false)
}

open class BaseRobot<A : Activity>(val activityTestRule: ActivityTestRule<A>) {

    /** Launch the [Activity] as defined by the [ActivityTestRule]*/
    fun launchActivity(startIntent: Intent? = null) {
        activityTestRule.launchActivity(startIntent)
        Thread.sleep(300)
    }

    fun editText_Replace(resId: Int, text: String): ViewInteraction =
        onView(ViewMatchers.withId(resId)).perform(ViewActions.replaceText(text), ViewActions.closeSoftKeyboard())

    fun editText_Type(resId: Int, text: String): ViewInteraction =
        onView(ViewMatchers.withId(resId)).perform(typeText(text), ViewActions.closeSoftKeyboard())

    fun button_Click(resId: Int): ViewInteraction = onView((ViewMatchers.withId(resId))).perform(ViewActions.click())

    fun textView(resId: Int): ViewInteraction = onView(ViewMatchers.withId(resId))

    fun matchText(viewInteraction: ViewInteraction, text: String): ViewInteraction = viewInteraction
        .check(matches(withText(text)))

    fun matchText(resId: Int, text: String): ViewInteraction = matchText(textView(resId), text)

    fun clickListItem(listRes: Int, position: Int) {
        Espresso.onData(Matchers.anything())
            .inAdapterView(Matchers.allOf(ViewMatchers.withId(listRes)))
            .atPosition(position).perform(ViewActions.click())
    }

    fun sleep() = apply {
        Thread.sleep(500)
    }

}