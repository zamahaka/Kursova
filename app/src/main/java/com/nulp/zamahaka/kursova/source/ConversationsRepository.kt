package com.nulp.zamahaka.kursova.source

import com.nulp.zamahaka.kursova.mvp.model.Conversation
import java.util.*
import kotlin.properties.Delegates

/**
 * Created by Ura on 02.04.2017.
 */

class ConversationsRepository
private constructor(private val mConversationsRemoteSource: ConversationsDataSource,
                    private val mConversationsLocalSource: ConversationsDataSource) : ConversationsDataSource {

    private val mCachedConversations: MutableMap<Int, Conversation> = LinkedHashMap()
    private var mCacheIsDirty = false

    override fun getConversations(callback: ConversationsDataSource.LoadConversationsCallback) {
        if (mCachedConversations.isNotEmpty() && !mCacheIsDirty) {
            callback.onConversationsLoaded(ArrayList(mCachedConversations.values))
            return
        }

        if (mCacheIsDirty) {
            // If the cache is dirty we need to fetch new data from the network.
            getTasksFromRemoteDataSource(callback)
        } else {
            // Query the local storage if available. If not, query the network.
            mConversationsLocalSource.getConversations(object : ConversationsDataSource.LoadConversationsCallback {
                override fun onConversationsLoaded(conversations: List<Conversation>) {
                    refreshCache(conversations)
                    callback.onConversationsLoaded(ArrayList(mCachedConversations.values))
                }

                override fun onDataNotAvailable() {
                    getTasksFromRemoteDataSource(callback)
                }
            })
        }
    }

    override fun saveConversation(conversation: Conversation) {
        mConversationsRemoteSource.saveConversation(conversation)
        mConversationsLocalSource.saveConversation(conversation)

        mCachedConversations.put(conversation.mId, conversation)
    }

    override fun refreshConversations() {
        mCacheIsDirty = true
    }

    override fun deleteConversation(conversation: Conversation) {
        mConversationsRemoteSource.deleteConversation(conversation)
        mConversationsLocalSource.deleteConversation(conversation)

        mCachedConversations.remove(conversation.mId)
    }

    override fun deleteAllConversations() {
        mConversationsRemoteSource.deleteAllConversations()
        mConversationsLocalSource.deleteAllConversations()

        mCachedConversations.clear()
    }

    private fun getTasksFromRemoteDataSource(callback: ConversationsDataSource.LoadConversationsCallback) {
        mConversationsRemoteSource.getConversations(object : ConversationsDataSource.LoadConversationsCallback {
            override fun onConversationsLoaded(conversations: List<Conversation>) {
                refreshCache(conversations)
                refreshLocalDataSource(conversations)
                callback.onConversationsLoaded(ArrayList(mCachedConversations.values))
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }
        })
    }

    private fun refreshCache(conversations: List<Conversation>) {
        mCachedConversations.clear()
        for (conversation in conversations) {
            mCachedConversations.put(conversation.mId, conversation)
        }
        mCacheIsDirty = false
    }

    private fun refreshLocalDataSource(conversations: List<Conversation>) {
        mConversationsLocalSource.deleteAllConversations()
        for (conversation in conversations) {
            mConversationsLocalSource.saveConversation(conversation)
        }
    }

    companion object {

        private var INSTANCE: ConversationsRepository? = null

        fun getInstance(conversationsRemoteSource: ConversationsDataSource,
                        conversationsLocalSource: ConversationsDataSource): ConversationsRepository {

            if (INSTANCE == null) {
                INSTANCE = ConversationsRepository(conversationsRemoteSource, conversationsLocalSource)
            }

            return INSTANCE ?: throw ConcurrentModificationException("illegal access to conversations repository instance")
        }
    }
}