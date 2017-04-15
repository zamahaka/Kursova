package com.nulp.zamahaka.kursova.mvp.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import com.nulp.zamahaka.kursova.mvp.AdapterView

/**
 * Created by Ura on 26.03.2017.
 */
abstract class BaseAdapter<T> :
        RecyclerView.Adapter<BaseAdapter.BaseViewHolder<T>>(),
        AdapterView<T> {

    override val mItems = ArrayList<T>()

    override fun getItemCount() = mItems.size

    override fun addItem(item: T, position: Int) {
        mItems.add(position, item)
        notifyItemInserted(position)
    }

    override fun addAll(items: List<T>, start: Int) {
        val size = mItems.size
        mItems.addAll(start, items)
        notifyItemRangeInserted(size, items.size)
    }

    override fun removeItem(item: T) {
        val index = mItems.indexOf(item)
        if (index > -1) {
            mItems.remove(item)
            notifyItemRemoved(index)
        }
    }

    override fun clear() {
        val size = mItems.size
        mItems.clear()
        notifyItemRangeRemoved(0, size)
    }

    abstract class BaseViewHolder<in T>(view: View) : RecyclerView.ViewHolder(view) {
        protected val mContext: Context
            get() = checkNotNull(itemView) { "itemView is null" }.context

        abstract fun bind(item: T)
    }

}