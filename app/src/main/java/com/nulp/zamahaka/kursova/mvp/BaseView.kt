package com.nulp.zamahaka.kursova.mvp

/**
 * Created by Ura on 26.03.2017.
 */
abstract interface BaseView<in T : BasePresenter> {

    fun setPresenter(presenter: T)

}