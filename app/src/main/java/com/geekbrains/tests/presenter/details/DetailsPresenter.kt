package com.geekbrains.tests.presenter.details

import com.geekbrains.tests.view.ViewContract
import com.geekbrains.tests.view.details.ViewDetailsContract

internal class DetailsPresenter : PresenterDetailsContract {

    private var viewContract: ViewDetailsContract? = null
    var count: Int = 0

    override fun setCounter(count: Int) {
        this.count = count
    }

    override fun onIncrement() {
        count++
        viewContract?.setCount(count)
    }

    override fun onDecrement() {
        count--
        viewContract?.setCount(count)
    }

    override fun onAttach(view: ViewContract) {
        viewContract = view as ViewDetailsContract
    }

    override fun onDetach() {
        viewContract = null
    }
}
