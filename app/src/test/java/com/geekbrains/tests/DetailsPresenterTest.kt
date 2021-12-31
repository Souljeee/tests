package com.geekbrains.tests

import android.os.Build
import android.widget.Button
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.geekbrains.tests.presenter.details.DetailsPresenter
import com.geekbrains.tests.view.details.DetailsActivity
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class DetailsPresenterTest {

    private lateinit var scenario: ActivityScenario<DetailsActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(DetailsActivity::class.java)
    }

    @Test
    fun setCounter_Test(){
        scenario.onActivity {
            val presenter = it.presenter as DetailsPresenter
            assertEquals(0,presenter.count)
        }
    }

    @Test
    fun onIncrement_Test(){
        scenario.onActivity {
            val incrementButton = it.findViewById<Button>(R.id.incrementButton)
            incrementButton.performClick()
            val presenter = it.presenter as DetailsPresenter
            assertEquals(1,presenter.count)
        }
    }

    @Test
    fun onDecrement_Test(){
        scenario.onActivity {
            val decrementButton = it.findViewById<Button>(R.id.decrementButton)
            decrementButton.performClick()
            val presenter = it.presenter as DetailsPresenter
            assertEquals(-1,presenter.count)
        }
    }

}