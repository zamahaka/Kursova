package com.nulp.zamahaka.kursova.source.remote

import android.content.Context
import android.os.Handler
import com.nulp.zamahaka.kursova.mvp.model.Conversation
import com.nulp.zamahaka.kursova.mvp.model.Gender
import com.nulp.zamahaka.kursova.mvp.model.Message
import com.nulp.zamahaka.kursova.mvp.model.User
import com.nulp.zamahaka.kursova.source.ConversationsDataSource

/**
 * Created by Ura on 08.04.2017.
 */
class RemoteConversationsDataSource
private constructor(context: Context) : ConversationsDataSource {

    private val mHandler = Handler()
    private val DELAY = 2000L

    override fun getConversations(callback: ConversationsDataSource.LoadCallback) {
        mHandler.postDelayed({
            callback.onConversationsLoaded(ArrayList(CONVERSATIONS_SERVICE_DATA.values))
        }, DELAY)
    }

    override fun getConversation(conversationId: Int,
                                 callback: ConversationsDataSource.GetCallback) =
            callback.onDataNotAvailable()

    override fun saveConversation(conversation: Conversation) {

    }

    override fun refreshConversations() {

    }

    override fun deleteConversation(conversationId: Int) {
        CONVERSATIONS_SERVICE_DATA.remove(conversationId)
    }

    override fun deleteAllConversations() {
        CONVERSATIONS_SERVICE_DATA.clear()
    }

    companion object {
        private var sInstance: RemoteConversationsDataSource? = null

        private val CONVERSATIONS_SERVICE_DATA = LinkedHashMap<Int, Conversation>()

        init {
            repeat(10) {
                CONVERSATIONS_SERVICE_DATA.put(it, createConversation(it))
            }
        }

        private fun createConversation(it: Int): Conversation =
                Conversation(it, "Title$it", "https://pp.userapi.com/c604624/v604624394/331db/04EJwTZSV24.jpg",
                        it - 1, Message(it, it, "text$it", User(it, "Name$it", "SurName$it",
                        "https://pp.userapi.com/c639117/v639117449/10482/G0qPv9BL0Cc.jpg",
                        System.currentTimeMillis(), Gender.MALE, System.currentTimeMillis(),
                        "Lviv", "NULP"
                ), System.currentTimeMillis()))

        fun getInstance(context: Context): RemoteConversationsDataSource {
            if (sInstance == null) {
                sInstance = RemoteConversationsDataSource(context)
            }

            return sInstance as RemoteConversationsDataSource
        }
    }
}