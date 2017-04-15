package com.nulp.zamahaka.kursova.mvp.contract

import com.nulp.zamahaka.kursova.mvp.*
import com.nulp.zamahaka.kursova.mvp.model.Conversation

/**
 * Created by Ura on 26.03.2017.
 */
interface ConversationListContract {

    interface View : BaseView<Presenter>, ActiveView, RequesView, AdapterView<Conversation> {

        fun showConversationList(conversations: List<Conversation>)
        fun setEmptyState(visible: Boolean)
        fun showLoadingError()
        fun showCreateConversation()

    }

    interface Presenter : BasePresenter {

        fun loadConversations(forceUpdate: Boolean)
        fun createConversation()
        fun deleteConversation(conversation: Conversation)

    }

}