package com.geekbrains.tests.view

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.geekbrains.tests.R
import com.geekbrains.tests.view.details.DetailsFragment
import com.geekbrains.tests.view.details.RecyclerAdapter
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailsFragmentTest {

    private lateinit var scenario: FragmentScenario<DetailsFragment>

    @Before
    public fun setup() {
        scenario = launchFragmentInContainer()
    }

    @Test
    fun fragment_AssertNotNull() {
        scenario.onFragment {
            TestCase.assertNotNull(it)
        }
    }

    @Test
    fun fragment_incrementButton_test() {
        onView(withId(R.id.incrementButton)).perform(click())

        onView(withId(R.id.totalCountTextView)).check(ViewAssertions.matches(ViewMatchers.withText("Number of results: 1")))
    }

    @Test
    fun fragment_ScrollTo() {
        onView(withId(R.id.recycler))
            .perform(
                RecyclerViewActions.scrollTo<RecyclerAdapter.ViewHolder>(
                    hasDescendant(withText("Item 42"))
                )
            )
    }
    @Test
    fun fragment_PerformClickAtPosition() {

        onView(withId(R.id.recycler))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerAdapter.ViewHolder>(
                    5,
                    click()
                )
            )
    }

    @Test
    fun activitySearch_PerformClickOnItem() {

        onView(withId(R.id.recycler))
            .perform(
                RecyclerViewActions.actionOnItem<RecyclerAdapter.ViewHolder>(
                    hasDescendant(withText("Item 80")),
                    click()
                )
            )
    }
}




