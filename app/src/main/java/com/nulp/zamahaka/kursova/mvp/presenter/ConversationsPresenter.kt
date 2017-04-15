package com.nulp.zamahaka.kursova.mvp.presenter

import com.nulp.zamahaka.kursova.isActiveOrFalse
import com.nulp.zamahaka.kursova.mvp.contract.ConversationListContract
import com.nulp.zamahaka.kursova.mvp.model.Conversation
import com.nulp.zamahaka.kursova.source.ConversationsDataSource
import com.nulp.zamahaka.kursova.source.ConversationsRepository

/**
 * Created by Ura on 26.03.2017.
 */
class ConversationsPresenter(private val mRepository: ConversationsRepository)
    : ConversationListContract.Presenter {

    var mView: ConversationListContract.View? = null

    private var mIsFirstLoad = true

    override fun start() = loadConversations(false)

    override fun loadConversations(forceUpdate: Boolean) {
        loadConversations(forceUpdate or mIsFirstLoad, true)
    }

    override fun createConversation() {
        performViewOperation { showCreateConversation() }
    }

    override fun deleteConversation(conversation: Conversation) {
        mRepository.deleteConversation(conversation.mId)
        performViewOperation { removeItem(conversation) }
    }

    private fun loadConversations(forceUpdate: Boolean, showLoadingUi: Boolean) {
        if (showLoadingUi) performViewOperation { setLoadingIndicator(true) }
        if (forceUpdate) mRepository.refreshConversations()
        mIsFirstLoad = false

        mRepository.getConversations(object : ConversationsDataSource.LoadCallback {
            override fun onConversationsLoaded(conversations: List<Conversation>) {
                performViewOperation {
                    if (showLoadingUi) setLoadingIndicator(false)
                    if (conversations.isEmpty()) setEmptyState(true) else showConversationList(conversations)
                }
            }

            override fun onDataNotAvailable() {
                performViewOperation {
                    if (showLoadingUi) setLoadingIndicator(false)
                    showLoadingError()
                }
            }
        })
    }

    private fun performViewOperation(operation: ConversationListContract.View.() -> Unit) {
        if (mView.isActiveOrFalse) mView?.operation()
    }
}