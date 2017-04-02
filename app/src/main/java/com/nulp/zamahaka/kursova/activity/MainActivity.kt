package com.nulp.zamahaka.kursova.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.nulp.zamahaka.kursova.R
import com.nulp.zamahaka.kursova.adapter.PagerAdapter
import com.nulp.zamahaka.kursova.mvp.presenter.ConversationsPresenter
import com.nulp.zamahaka.kursova.mvp.view.fragment.ConversationListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolbar()
        initViews()

    }

    fun initToolbar() {
        setSupportActionBar(toolBar)
    }

    private fun initViews() {
        tabLayout.setupWithViewPager(viewPager)
        val adapter = PagerAdapter(supportFragmentManager)
        val conversationsFragment = ConversationListFragment.newInstance()
        conversationsFragment.setPresenter(ConversationsPresenter(conversationsFragment))
        adapter.addFragment(conversationsFragment)

        viewPager.adapter = adapter
    }
}
