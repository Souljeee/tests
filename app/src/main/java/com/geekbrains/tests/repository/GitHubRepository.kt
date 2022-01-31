package com.geekbrains.tests.repository

import com.geekbrains.tests.model.SearchResponse
import com.geekbrains.tests.presenter.search.RepositoryContract
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class GitHubRepository(private val gitHubApi: GitHubApi): RepositoryContract {

    fun searchGithub(
        query: String,
        callback: GitHubRepositoryCallback
    ) {
        val call = gitHubApi.searchGithub(query)
        call?.enqueue(object : Callback<SearchResponse?> {

            override fun onResponse(
                call: Call<SearchResponse?>,
                response: Response<SearchResponse?>
            ) {
                callback.handleGitHubResponse(response)
            }

            override fun onFailure(
                call: Call<SearchResponse?>,
                t: Throwable
            ) {
                callback.handleGitHubError()
            }
        })
    }

    interface GitHubRepositoryCallback {
        fun handleGitHubResponse(response: Response<SearchResponse?>?)
        fun handleGitHubError()
    }

    override fun searchGithub(query: String): Observable<SearchResponse> {
        return gitHubApi.searchGithubRx(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override suspend fun searchGithubAsync(query: String): SearchResponse {
        return gitHubApi.searchGithubAsync(query).await()
    }
}
