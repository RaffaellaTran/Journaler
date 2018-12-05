package com.example.rafaellat.journaler.UITest

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.rafaellat.journaler.R
import com.example.rafaellat.journaler.activity.NoteActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NoteScreenTest {

    @Rule
    @JvmField
    val noteActivity = ActivityTestRule(NoteActivity::class.java)

    @Test
    fun changeContentText() {
        onView(withId(R.id.note_content))
            .perform(replaceText("Test"))
            .check(matches(isDisplayed()))

    }

    @Test
    fun changeTitleText() {
        onView(withId(R.id.note_title))
            .perform(replaceText("Hello"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun sendNoteCorrectly() {

        changeContentText()
        changeTitleText()
        if (R.id.indicator == R.color.green)
            onView(withId(R.id.confirm_button)).perform(click())
    }

}