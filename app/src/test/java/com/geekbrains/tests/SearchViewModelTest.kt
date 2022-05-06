package com.geekbrains.tests

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.geekbrains.tests.model.SearchResponse
import com.geekbrains.tests.presenter.search.ScreenState
import com.geekbrains.tests.presenter.search.SearchSchedulerProvider
import com.geekbrains.tests.presenter.search.SearchViewModel
import com.geekbrains.tests.repository.FakeGitHubRepository
import com.geekbrains.tests.repository.GitHubRepository
import io.reactivex.*
import io.reactivex.Observable

import io.reactivex.disposables.Disposable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.*


import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.robolectric.annotation.Config
import java.util.*


@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
@ExperimentalCoroutinesApi
class SearchViewModelTest {

    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var searchViewModel: SearchViewModel

    private lateinit var liveData: LiveData<ScreenState>
    private lateinit var observer: Observer<ScreenState>

    @Mock
    private lateinit var repository: FakeGitHubRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        searchViewModel = SearchViewModel(repository, SearchSchedulerProvider())
        liveData = searchViewModel.subscribeToLiveData()
        observer = Observer<ScreenState> { }
        liveData.observeForever(observer)
    }

    @Test
    fun search_Test() {
        Mockito.`when`(repository.searchGithub(SEARCH_QUERY)).thenReturn(
            Observable.just(
                SearchResponse(
                    1,
                    listOf()
                )
            )
        )

        searchViewModel.searchGitHub(SEARCH_QUERY)
        verify(repository, times(1)).searchGithub(SEARCH_QUERY)
    }

    @Test
    fun liveData_TestReturnValueIsNotNull() {
        Mockito.`when`(repository.searchGithub(SEARCH_QUERY)).thenReturn(
            Observable.just(
                SearchResponse(
                    1,
                    listOf()
                )
            )
        )
        searchViewModel.searchGitHub(SEARCH_QUERY)
        Assert.assertNotNull(liveData.value)
    }

    @Test
    fun liveData_TestReturnValueIsSuccess() {
        val response = SearchResponse(1, listOf())

        Mockito.`when`(repository.searchGithub(SEARCH_QUERY)).thenReturn(
            Observable.just(
                response
            )
        )
        searchViewModel.searchGitHub(SEARCH_QUERY)
        val value: ScreenState.Working = liveData.value as ScreenState.Working
        Assert.assertEquals(response, value.searchResponse)
    }

    @Test
    fun liveData_TestReturnValueIsError() {

        val error = Throwable(ERROR_TEXT)

        Mockito.`when`(repository.searchGithub(SEARCH_QUERY)).thenReturn(
            Observable.error(error)
        )
        searchViewModel.searchGitHub(SEARCH_QUERY)
        val value: ScreenState.Error = liveData.value as ScreenState.Error
        Assert.assertEquals(value.error.message, error.message)
    }

    @Test
    fun coroutines_TestReturnValueIsNotNull() {
        testCoroutineRule.runBlockingTest {
            val observer = Observer<ScreenState> {}
            val liveData = searchViewModel.subscribeToLiveData()

            `when`(repository.searchGithubAsync(SEARCH_QUERY)).thenReturn(
                SearchResponse(1, listOf())
            )

            try {
                liveData.observeForever(observer)
                searchViewModel.searchGitHub(SEARCH_QUERY)
                Assert.assertNotNull(liveData.value)
            } finally {
                liveData.removeObserver(observer)
            }
        }
    }

    @Test
    fun coroutines_TestReturnValueIsError() {
        testCoroutineRule.runBlockingTest {
            `when`(repository.searchGithubAsync(SEARCH_QUERY)).thenReturn(
                SearchResponse(null, listOf())
            )
            searchViewModel.searchGitHub(SEARCH_QUERY)
            val value: ScreenState.Error = liveData.value as ScreenState.Error
            Assert.assertEquals(value.error.message, ERROR_TEXT)

        }
    }

    @Test
    fun coroutines_TestReturnValueIsSuccess() {
        testCoroutineRule.runBlockingTest {
            val response = SearchResponse(1, listOf())

            `when`(repository.searchGithubAsync(SEARCH_QUERY)).thenReturn(response)
            searchViewModel.searchGitHub(SEARCH_QUERY)
            val value: ScreenState.Working = liveData.value as ScreenState.Working
            Assert.assertEquals(response, value.searchResponse)
        }
    }


    @After
    fun after() {
        liveData.removeObserver(observer)
    }


    companion object {
        private const val SEARCH_QUERY = "some query"
        private const val ERROR_TEXT = "error"
    }
}