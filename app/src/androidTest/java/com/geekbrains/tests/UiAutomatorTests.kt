package com.geekbrains.tests

import android.content.Context
import android.content.Intent
import android.widget.TextView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class UiAutomatorTests {

    private val uiDevice: UiDevice = UiDevice.getInstance(getInstrumentation())

    private val context = ApplicationProvider.getApplicationContext<Context>()

    private val packageName = context.packageName

    @Before
    fun setup() {
        uiDevice.pressHome()

        val intent = context.packageManager.getLaunchIntentForPackage(packageName)

        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)

        uiDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), 5000L)
    }

    @Test
    fun test_DeviceNotNull() {
        Assert.assertNotNull(uiDevice)
    }

    @Test
    fun open_otherApps() {
        uiDevice.pressHome()

        uiDevice.swipe(500, 1500, 500, 0, 5)

        val appViews = UiScrollable(UiSelector().scrollable(true))
        appViews.setAsHorizontalList()

        val settingsApp = appViews
            .getChildByText(
                UiSelector()
                    .className(TextView::class.java.name),
                "Spotify"
            )

        settingsApp.clickAndWaitForNewWindow()
    }

    @Test
    fun test_OpenDetailsScreen() {

        val toDetails = uiDevice.findObject(By.res(packageName, "toDetailsActivityButton"))

        toDetails.click()

        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "totalCountTextView")),
                5000L
            )

        Assert.assertEquals(changedText.text, "Number of results: 0")
    }

    @Test
    fun test_CounterRightAnswer() {

        val toDetails = uiDevice.findObject(By.res(packageName, "toDetailsActivityButton"))

        toDetails.click()

        val increment = uiDevice.wait(
            Until.findObject(By.res(packageName, "incrementButton")),
            5000L
        )

        increment.click()


        Espresso.onView(ViewMatchers.withId(R.id.totalCountTextView))
            .check(ViewAssertions.matches(ViewMatchers.withText("Number of results: 1")))
    }

}