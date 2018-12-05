package com.example.rafaellat.journaler.UITest

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.rafaellat.journaler.R
import com.example.rafaellat.journaler.activity.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainScreenTest {
    @Rule @JvmField
    val mainActivityRule = ActivityTestRule(MainActivity::class.java)


    @Test
    fun testMainActivity(){

        onView((withId(R.id.toolbar))).perform(click())
        //onView(withId(R.id.))
      //  onView((withText("Ciao"))).check(matches(isDisplayed()))
    }

    @Test
    fun OpenLeftMenu() {
        onView(withId(R.id.left_drawer)).perform(click())
    }

}