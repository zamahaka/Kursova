package com.nulp.zamahaka.kursova.source

import com.nulp.zamahaka.kursova.mvp.model.Conversation

/**
 * Created by Ura on 02.04.2017.
 */
interface ConversationsDataSource {

    interface LoadCallback {

        fun onConversationsLoaded(conversations: List<Conversation>)

        fun onDataNotAvailable()
    }

    interface GetCallback {

        fun onConversationsLoaded(conversation: Conversation)

        fun onDataNotAvailable()
    }

    fun getConversations(callback: LoadCallback)

    fun getConversation(conversationId: Int, callback: GetCallback)

    fun saveConversation(conversation: Conversation)

    fun refreshConversations()

    fun deleteConversation(conversationId: Int)

    fun deleteAllConversations()

}