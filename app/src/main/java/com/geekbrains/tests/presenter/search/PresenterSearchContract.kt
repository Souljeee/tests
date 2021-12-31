package com.geekbrains.tests.presenter.search

import com.geekbrains.tests.presenter.PresenterContract

interface PresenterSearchContract : PresenterContract {
    fun searchGitHub(searchQuery: String)
    //onAttach
    //onDetach
}
