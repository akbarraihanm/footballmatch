package com.example.akbar.retrofitsample

import android.support.test.espresso.Espresso.*
import android.support.test.espresso.action.Swipe
import android.support.test.espresso.action.Swiper
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.scrollTo
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import com.example.akbar.retrofitsample.Adapter.LastFixtureAdapter
import com.example.akbar.retrofitsample.Fragment.FragmentLastFixture
import com.example.akbar.retrofitsample.R.id.*
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest{
    @Rule
    @JvmField var activityRule = ActivityTestRule(HomeActivity::class.java)
    @Test
    fun testAppBehaviour(){
        Thread.sleep(2000)
//        onView(withId(rv_lastFixture))
//            .check(matches(hasFocus()))
//        onView(allOf(withParent(withId(R.layout.fragment_fragment_last_fixture)), withId(R.id.rv_lastFixture)))
//            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))
        onView(allOf(withId(rv_lastFixture), isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))
        Thread.sleep(2000)
        onView(withId(add_to_favorite)).perform(click())
        pressBack()
        Thread.sleep(2000)
        onView(withText(" Next Fixture ")).perform(click())
        Thread.sleep(2000)
        onView(allOf(withId(rv_lastFixture), isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(3, click()))
        Thread.sleep(2000)
        onView(withId(add_to_favorite)).perform(click())
        pressBack()
//        onView(withId(R.id.tabs))
//            .check(matches(isDisplayed()))
//        onView(withText("05/11/18"))
//            .perform(click())
//        Thread.sleep(3_000)
//        onView(withId(R.id.add_to_favorite)).perform(click())
//        pressBack()
//        Thread.sleep(3_000)
//        onView(withId(R.id.tabs))
//            .check(matches(isDisplayed()))
//        onView(withText(" Favorite Fixture "))
//            .perform(click())
//            .check(matches(isDisplayed()))
    }
}