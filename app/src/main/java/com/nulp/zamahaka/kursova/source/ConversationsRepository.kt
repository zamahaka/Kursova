package com.nulp.zamahaka.kursova.source

import com.nulp.zamahaka.kursova.mvp.model.Conversation
import java.util.*

/**
 * Created by Ura on 02.04.2017.
 */

class ConversationsRepository
private constructor(private val mRemoteConversationsSource: ConversationsDataSource,
                    private val mLocalConversationsSource: ConversationsDataSource) : ConversationsDataSource {

    private val mCache: MutableMap<Int, Conversation> = LinkedHashMap()
    private var mCacheIsDirty = false

    override fun getConversations(callback: ConversationsDataSource.LoadCallback) {
        if (mCache.isNotEmpty() && !mCacheIsDirty) {
            callback.onConversationsLoaded(ArrayList(mCache.values))
            return
        }

        if (mCacheIsDirty) {
            // If the cache is dirty we need to fetch new data from the network.
            getConversationsFromRemoteDataSource(callback)
        } else {
            // Query the local storage if available. If not, query the network.
            mLocalConversationsSource.getConversations(object : ConversationsDataSource.LoadCallback {
                override fun onConversationsLoaded(conversations: List<Conversation>) {
                    refreshCache(conversations)
                    callback.onConversationsLoaded(ArrayList(mCache.values))
                }

                override fun onDataNotAvailable() {
                    getConversationsFromRemoteDataSource(callback)
                }
            })
        }
    }

    override fun getConversation(conversationId: Int, callback: ConversationsDataSource.GetCallback) =
            mCache[conversationId]?.let { callback.onConversationsLoaded(it) } ?: mLocalConversationsSource
                    .getConversation(conversationId, object : ConversationsDataSource.GetCallback {
                        override fun onConversationsLoaded(conversation: Conversation) {
                            callback.onConversationsLoaded(conversation)
                        }

                        override fun onDataNotAvailable() {
                            mRemoteConversationsSource.getConversation(conversationId, object : ConversationsDataSource.GetCallback {
                                override fun onConversationsLoaded(conversation: Conversation) {
                                    callback.onConversationsLoaded(conversation)
                                }

                                override fun onDataNotAvailable() {
                                    callback.onDataNotAvailable()
                                }
                            })
                        }
                    })

    override fun saveConversation(conversation: Conversation) {
        performSourcesOperation { saveConversation(conversation) }

        mCache.put(conversation.mId, conversation)
    }

    override fun refreshConversations() {
        mCacheIsDirty = true
    }

    override fun deleteConversation(conversationId: Int) {
        performSourcesOperation { deleteConversation(conversationId) }

        mCache.remove(conversationId)
    }

    override fun deleteAllConversations() {
        performSourcesOperation { deleteAllConversations() }

        mCache.clear()
    }

    private fun getConversationsFromRemoteDataSource(callback: ConversationsDataSource.LoadCallback) {
        mRemoteConversationsSource.getConversations(object : ConversationsDataSource.LoadCallback {
            override fun onConversationsLoaded(conversations: List<Conversation>) {
                refreshCache(conversations)
                refreshLocalDataSource(conversations)
                callback.onConversationsLoaded(ArrayList(mCache.values))
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }
        })
    }

    private fun refreshCache(conversations: List<Conversation>) {
        mCache.clear()
        for (conversation in conversations) {
            mCache.put(conversation.mId, conversation)
        }
        mCacheIsDirty = false
    }

    private fun refreshLocalDataSource(conversations: List<Conversation>) {
        mLocalConversationsSource.deleteAllConversations()
        for (conversation in conversations) {
            mLocalConversationsSource.saveConversation(conversation)
        }
    }

    private fun performSourcesOperation(operation: ConversationsDataSource.() -> Unit) {
        mRemoteConversationsSource.operation()
        mLocalConversationsSource.operation()
    }

    companion object {

        private var sInstance: ConversationsRepository? = null

        fun getInstance(conversationsRemoteSource: ConversationsDataSource,
                        conversationsLocalSource: ConversationsDataSource): ConversationsRepository {

            if (sInstance == null) {
                sInstance = ConversationsRepository(conversationsRemoteSource, conversationsLocalSource)
            }

            return sInstance as ConversationsRepository
        }
    }
}