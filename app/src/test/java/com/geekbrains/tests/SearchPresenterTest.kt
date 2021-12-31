package com.geekbrains.tests

import android.os.Build
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.geekbrains.tests.model.SearchResponse
import com.geekbrains.tests.model.SearchResult
import com.geekbrains.tests.presenter.search.SearchPresenter
import com.geekbrains.tests.repository.GitHubRepository
import com.geekbrains.tests.view.details.DetailsActivity
import com.geekbrains.tests.view.search.MainActivity
import com.geekbrains.tests.view.search.ViewSearchContract
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.robolectric.annotation.Config
import retrofit2.Response

//Тестируем наш Презентер
@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class SearchPresenterTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }


    @Test
    fun onAttach_Test(){
        scenario.onActivity {
            val presenter = it.presenter as SearchPresenter
            it.presenter.onAttach(it)
            assertNotNull(presenter.viewContract)
        }
    }

    @Test
    fun onDetach_Test(){
        scenario.onActivity {
            val presenter = it.presenter as SearchPresenter
            it.presenter.onDetach()
            assertNull(presenter.viewContract)
        }
    }
}
