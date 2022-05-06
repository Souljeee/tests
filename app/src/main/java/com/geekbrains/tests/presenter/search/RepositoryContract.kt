package com.geekbrains.tests.presenter.search

import com.geekbrains.tests.model.SearchResponse
import io.reactivex.Observable

interface RepositoryContract {


    fun searchGithub(
        query: String
    ): Observable<SearchResponse>

    suspend fun searchGithubAsync(
        query: String
    ): SearchResponse
}