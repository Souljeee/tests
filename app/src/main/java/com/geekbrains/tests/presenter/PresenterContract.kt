package com.geekbrains.tests.presenter

import com.geekbrains.tests.view.ViewContract

interface PresenterContract {
    fun onAttach(view : ViewContract)
    fun onDetach()
}
