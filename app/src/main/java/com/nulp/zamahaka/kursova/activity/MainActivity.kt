package com.nulp.zamahaka.kursova.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.nulp.zamahaka.kursova.R
import com.nulp.zamahaka.kursova.mvp.presenter.ConversationPresenter
import com.nulp.zamahaka.kursova.mvp.view.fragment.ConversationListFragment
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Picasso.with(this).isLoggingEnabled = true

        initViews()
    }

    private fun initViews() {
        val fragment = ConversationListFragment.newInstance()
        fragment.setPresenter(ConversationPresenter(fragment))
        supportFragmentManager.beginTransaction()
                .add(R.id.tempContainer, fragment)
                .commit()
    }
}
