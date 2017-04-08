package com.nulp.zamahaka.kursova.mvp.presenter

import com.nulp.zamahaka.kursova.mvp.contract.ConversationListContract
import com.nulp.zamahaka.kursova.mvp.model.Conversation
import com.nulp.zamahaka.kursova.source.ConversationsDataSource
import com.nulp.zamahaka.kursova.source.ConversationsRepository

/**
 * Created by Ura on 26.03.2017.
 */
class ConversationsPresenter(private val mView: ConversationListContract.View,
                             private val mRepository: ConversationsRepository)
    : ConversationListContract.Presenter {

    private var mIsFirstLoad = true

    override fun start() {
        loadConversations(false)
    }

    override fun loadConversations(forceUpdate: Boolean) {
        loadConversations(forceUpdate or mIsFirstLoad, true)
        mIsFirstLoad = false
    }

    override fun createConversation(with: List<Int>) {

    }

    override fun deleteConversation(conversationId: Int) {
        mRepository.deleteConversation(conversationId)
        performViewOperation { removeConversation(conversationId) }
    }

    private fun loadConversations(forceUpdate: Boolean, showLoadingUi: Boolean) {
        if (showLoadingUi) performViewOperation { setLoadingIndicator(true) }
        if (forceUpdate) mRepository.refreshConversations()

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
        if (mView.isActive) mView.operation()
    }
}