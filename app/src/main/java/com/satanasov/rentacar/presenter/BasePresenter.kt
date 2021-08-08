package com.satanasov.rentacar.presenter

abstract class BasePresenter<T> {
    protected var view: T?          = null
    protected var isSubscribed      = false
    protected var isFirstSubscribe  = true

    fun attachView(view: T) {
        this.view = view
    }

   open fun subscribe() {
        isSubscribed = true
    }

    fun unSubscribe() {
        isSubscribed        = false
        isFirstSubscribe    = false
    }

    fun detachView() {
        view = null
    }
}