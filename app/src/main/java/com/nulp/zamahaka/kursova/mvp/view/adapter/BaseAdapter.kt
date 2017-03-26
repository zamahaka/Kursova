package com.nulp.zamahaka.kursova.mvp.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by Ura on 26.03.2017.
 */
abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseAdapter.BaseViewHolder<T>>() {

    val mItems = ArrayList<T>()

    abstract class BaseViewHolder<in T>(view: View) : RecyclerView.ViewHolder(view) {
        protected val mContext: Context
            get() = checkNotNull(itemView) { "itemView is null" }.context

        abstract fun bind(item: T)
    }

}