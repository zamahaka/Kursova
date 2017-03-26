package com.nulp.zamahaka.kursova.mvp.contract

import com.nulp.zamahaka.kursova.mvp.BasePresenter
import com.nulp.zamahaka.kursova.mvp.BaseView
import com.nulp.zamahaka.kursova.mvp.model.Conversation

/**
 * Created by Ura on 26.03.2017.
 */
interface ConversationListContract {

    interface View : BaseView<Presenter> {

        fun showConversationList(conversations: List<Conversation>)
        fun addConversation(conversation: Conversation)
        fun removeConversation(id: Int)

    }

    interface Presenter : BasePresenter {

        fun loadConversationList()
        fun addConversation(with: List<Int>)
        fun deleteConversation(id: Int)

    }

}