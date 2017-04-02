package com.nulp.zamahaka.kursova.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * Created by Ura on 02.04.2017.
 */
class PagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    private val mFragments: MutableList<Fragment> = ArrayList()

    override fun getItem(position: Int) = mFragments[position]

    override fun getCount() = mFragments.size

    override fun getPageTitle(position: Int) = if (position == 0) "Conversations" else "Empty"

    fun addFragment(fragment: Fragment) {
        mFragments.add(fragment)
    }

}