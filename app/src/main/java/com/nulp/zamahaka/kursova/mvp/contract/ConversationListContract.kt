package com.nulp.zamahaka.kursova.mvp.contract

import com.nulp.zamahaka.kursova.mvp.ActiveView
import com.nulp.zamahaka.kursova.mvp.BasePresenter
import com.nulp.zamahaka.kursova.mvp.BaseView
import com.nulp.zamahaka.kursova.mvp.RequesView
import com.nulp.zamahaka.kursova.mvp.model.Conversation

/**
 * Created by Ura on 26.03.2017.
 */
interface ConversationListContract {

    interface View : BaseView<Presenter>, ActiveView, RequesView {

        fun showConversationList(conversations: List<Conversation>)
        fun addConversation(conversation: Conversation)
        fun removeConversation(conversationId: Int)
        fun setEmptyState(visible: Boolean)
        fun showLoadingError()

    }

    interface Presenter : BasePresenter {

        fun loadConversations(forceUpdate: Boolean)
        fun createConversation(with: List<Int>)
        fun deleteConversation(conversationId: Int)

    }

}