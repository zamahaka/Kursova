package com.nulp.zamahaka.kursova.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.nulp.zamahaka.kursova.Injection
import com.nulp.zamahaka.kursova.R
import com.nulp.zamahaka.kursova.base.TagFragment
import com.nulp.zamahaka.kursova.mvp.contract.ConversationListContract
import com.nulp.zamahaka.kursova.mvp.presenter.ConversationsPresenter
import com.nulp.zamahaka.kursova.mvp.view.fragment.ConversationsFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    private var mConversationsPresenter: ConversationsPresenter by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

        val fragment: Fragment? = supportFragmentManager.findFragmentById(R.id.content)
        fragment?.let {
            when (fragment) {
                is ConversationListContract.View ->
                    mConversationsPresenter = createConversationsPresenter(fragment)
                is EmptyFragment -> {
                }
            }
        } ?: when (navigationView.selectedItemId) {
            R.id.navigation_main_conversation -> {
                val conversationsFragment = ConversationsFragment.newInstance()
                mConversationsPresenter = createConversationsPresenter(conversationsFragment)
                replaceFragment(conversationsFragment)
            }
            R.id.navigation_main_profile -> {
                val emptyFragment = EmptyFragment()
                replaceFragment(emptyFragment)
            }
        }
    }

    private fun initViews() {
        navigationView.setOnNavigationItemSelectedListener { item -> onNavigationItemSelected(item) }
        navigationView.setOnNavigationItemReselectedListener { }
    }

    private fun onNavigationItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.navigation_main_conversation -> {
            val conversationsFragment = ConversationsFragment.newInstance()
            mConversationsPresenter = createConversationsPresenter(conversationsFragment)
            replaceFragment(conversationsFragment)
            true
        }
        R.id.navigation_main_profile -> {
            addProfileFragment(); true
        }
        else -> false
    }

    private fun createConversationsPresenter(view: ConversationListContract.View) = ConversationsPresenter(view, Injection.provideTasksRepository(this))

    private fun addProfileFragment() {
        val fragment = EmptyFragment()

        replaceFragment(fragment)
    }

    private fun replaceFragment(fragment: TagFragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.content, fragment, fragment.TAG)
                .commit()
    }

    class EmptyFragment : TagFragment() {
        override val TAG = "empty"

        override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return FrameLayout(container?.context)
        }
    }
}
