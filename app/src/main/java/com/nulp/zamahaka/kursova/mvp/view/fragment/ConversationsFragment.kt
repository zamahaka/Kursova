package com.nulp.zamahaka.kursova.mvp.view.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.*
import com.nulp.zamahaka.kursova.R
import com.nulp.zamahaka.kursova.base.TagFragment
import com.nulp.zamahaka.kursova.controller.ErrorController
import com.nulp.zamahaka.kursova.controller.NoInternetController
import com.nulp.zamahaka.kursova.listener.ConversationListener
import com.nulp.zamahaka.kursova.mvp.contract.ConversationListContract
import com.nulp.zamahaka.kursova.mvp.model.Conversation
import com.nulp.zamahaka.kursova.mvp.view.adapter.ConversationListAdapter
import kotlinx.android.synthetic.main.fragment_conversation_list.*
import kotlin.properties.Delegates

/**
 * Created by Ura on 26.03.2017.
 */
class ConversationsFragment : TagFragment(), ConversationListContract.View, ConversationListener {
    override val TAG = "ConversationsFragment"
    override val isActive get() = isAdded
    override val mItems get() = mAdapter.mItems

    private val mAdapter by lazy { ConversationListAdapter(this) }

    private var mPresenter: ConversationListContract.Presenter? = null

    private val mNoInternetController by lazy { NoInternetController(context) }
    private val mErrorController by lazy { ErrorController(context, R.string.error_message_conversations_loading) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_conversation_list, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        initViews()
        mPresenter?.start()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.conversations_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.conversation_menu_create -> {
            mPresenter?.createConversation()
            true
        }
        else -> false
    }

    override fun setPresenter(presenter: ConversationListContract.Presenter) {
        mPresenter = presenter
    }

    override fun showConversationList(conversations: List<Conversation>) {
        mAdapter.clear()
        mAdapter.addAll(conversations)
    }

    override fun addItem(item: Conversation, position: Int) = mAdapter.addItem(item, position)

    override fun addAll(items: List<Conversation>, start: Int) = mAdapter.addAll(items, start)

    override fun removeItem(item: Conversation) = mAdapter.removeItem(item)

    override fun clear() = mAdapter.clear()

    override fun deleteConversation(conversation: Conversation) {
        mPresenter?.deleteConversation(conversation)
    }

    override fun showCreateConversation() {
        // TODO: 08.04.2017 start conversation creating
    }

    override fun setLoadingIndicator(active: Boolean) {
        swipeRefreshLayout.isRefreshing = active
    }

    override fun showNoInternet() {
        mNoInternetController.show()
    }

    override fun setEmptyState(visible: Boolean) = if (visible) {
        swipeRefreshLayout.visibility = View.GONE
        message.visibility = View.VISIBLE
    } else {
        swipeRefreshLayout.visibility = View.VISIBLE
        message.visibility = View.GONE
    }

    override fun showLoadingError() {
        mErrorController.show()
    }

    private fun initViews() {
        recyclerConversations.layoutManager = LinearLayoutManager(context)
        recyclerConversations.adapter = mAdapter

        swipeRefreshLayout.setOnRefreshListener { mPresenter?.loadConversations(true) }
    }

    companion object {
        @JvmStatic fun newInstance(): ConversationsFragment {
            return ConversationsFragment()
        }
    }
}