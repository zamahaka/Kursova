package com.nulp.zamahaka.kursova.mvp.presenter

import com.nulp.zamahaka.kursova.mvp.contract.ConversationListContract
import com.nulp.zamahaka.kursova.mvp.model.Conversation
import com.nulp.zamahaka.kursova.mvp.model.Message
import com.nulp.zamahaka.kursova.mvp.model.User

/**
 * Created by Ura on 26.03.2017.
 */
class ConversationPresenter(private val mView: ConversationListContract.View)
    : ConversationListContract.Presenter {

    private val mConversations: MutableList<Conversation> by lazy {
        createConversations()
    }

    private fun createConversations(): MutableList<Conversation> {
        return (0..10).mapTo(ArrayList()) { createConversation(it) }
    }

    private fun createConversation(it: Int): Conversation {
        return Conversation(it, "title$it", "https://pp.userapi.com/c604624/v604624394/331db/04EJwTZSV24.jpg", it - 1, Message(it, it, "text$it", User(
                it, "Name$it", "SurName$it", "https://pp.userapi.com/c639117/v639117449/10482/G0qPv9BL0Cc.jpg", System.currentTimeMillis(), listOf(it),
                1, System.currentTimeMillis(), "Lviv", "NULP"
        ), System.currentTimeMillis()))
    }

    override fun start() {
        loadConversationList()
    }

    override fun loadConversationList() {
        mView.showConversationList(mConversations)
    }

    override fun addConversation(with: List<Int>) {
        val conversation = createConversation(mConversations.size)
        mConversations.add(conversation)
        mView.addConversation(conversation)
    }

    override fun deleteConversation(id: Int) {
        mConversations.removeAt(mConversations.indexOfFirst { it.mId == id })
        mView.removeConversation(id)
    }
}