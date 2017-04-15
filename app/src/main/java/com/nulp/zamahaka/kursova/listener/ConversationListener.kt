package com.nulp.zamahaka.kursova.listener

import com.nulp.zamahaka.kursova.mvp.model.Conversation

/**
 * Created by Ura on 26.03.2017.
 */
interface ConversationListener {

    fun deleteConversation(conversation: Conversation)

}