package com.nulp.zamahaka.kursova.source

import com.nulp.zamahaka.kursova.mvp.model.Conversation

/**
 * Created by Ura on 02.04.2017.
 */
interface ConversationsDataSource {

    interface LoadConversationsCallback {

        fun onConversationsLoaded(conversations: List<Conversation>)

        fun onDataNotAvailable()
    }

    fun getConversations(callback: LoadConversationsCallback)

    fun saveConversation(conversation: Conversation)

    fun refreshConversations()

    fun deleteConversation(conversation: Conversation)

    fun deleteAllConversations()

}