package com.nulp.zamahaka.kursova.mvp

/**
 * Created by Ura on 15.04.2017.
 */
interface AdapterView<T> {

    val mItems: List<T>

    fun addItem(item: T, position: Int = mItems.size)

    fun addAll(items: List<T>, start: Int = mItems.size)

    fun removeItem(item: T)

    fun clear()
}