package com.nulp.zamahaka.kursova.source.local

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.nulp.zamahaka.kursova.mvp.model.Conversation
import com.nulp.zamahaka.kursova.mvp.model.Gender
import com.nulp.zamahaka.kursova.mvp.model.Message
import com.nulp.zamahaka.kursova.mvp.model.User
import com.nulp.zamahaka.kursova.queryDef
import com.nulp.zamahaka.kursova.source.ConversationsDataSource
import com.nulp.zamahaka.kursova.source.local.database.entry.ConversationEntry
import com.nulp.zamahaka.kursova.source.local.database.helper.ConversationDbHelper

/**
 * Created by Ura on 08.04.2017.
 */
class LocalConversationsDataSource
private constructor(context: Context) : ConversationsDataSource {

    private val mDbHelper = ConversationDbHelper(context)

    override fun getConversations(callback: ConversationsDataSource.LoadCallback) {
        val conversations = ArrayList<Conversation>()
        val db = mDbHelper.readableDatabase

        val c = db.queryDef(ConversationEntry.TABLE_NAME)

        if (c.count > 0) {
            while (c.moveToNext()) {
                conversations.add(createConversation(c))
            }
        }

        c.close()
        db.close()

        if (conversations.isEmpty()) callback.onDataNotAvailable() else callback.onConversationsLoaded(conversations)
    }

    override fun getConversation(conversationId: Int, callback: ConversationsDataSource.GetCallback) {
        val db = mDbHelper.readableDatabase

        val selection = "${ConversationEntry.COLUMN_NAME_ID} LIKE $conversationId"

        val c = db.queryDef(ConversationEntry.TABLE_NAME, selection = selection)

        var conversation: Conversation? = null

        if (c.count > 0) {
            c.moveToFirst()
            conversation = createConversation(c)
        }

        c.close()
        db.close()

        conversation?.let { callback.onConversationsLoaded(it) } ?: callback.onDataNotAvailable()
    }

    override fun saveConversation(conversation: Conversation) {
        val db = mDbHelper.writableDatabase

        val values = createContentValues(conversation)

        db.insertWithOnConflict(ConversationEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE)

        db.close()
    }

    override fun refreshConversations() {

    }

    override fun deleteConversation(conversationId: Int) {
        val db = mDbHelper.writableDatabase

        val selection = "${ConversationEntry.COLUMN_NAME_ID} LIKE $conversationId"

        db.delete(ConversationEntry.TABLE_NAME, selection, null)

        db.close()
    }

    override fun deleteAllConversations() {
        val db = mDbHelper.writableDatabase

        db.delete(ConversationEntry.TABLE_NAME, null, null)

        db.close()
    }

    private fun createConversation(cursor: Cursor) = Conversation(
            cursor.getInt(cursor.getColumnIndex(ConversationEntry.COLUMN_NAME_ID)),
            cursor.getString(cursor.getColumnIndex(ConversationEntry.COLUMN_NAME_TITLE)),
            cursor.getString(cursor.getColumnIndex(ConversationEntry.COLUMN_NAME_AVATAR)),
            cursor.getInt(cursor.getColumnIndex(ConversationEntry.COLUMN_NAME_UNREAD)),
            Message(
                    cursor.getInt(cursor.getColumnIndex(ConversationEntry.COLUMN_NAME_MESSAGE_ID)),
                    cursor.getInt(cursor.getColumnIndex(ConversationEntry.COLUMN_NAME_ID)),
                    cursor.getString(cursor.getColumnIndex(ConversationEntry.COLUMN_NAME_MESSAGE_TEXT)),
                    User(
                            cursor.getInt(cursor.getColumnIndex(ConversationEntry.COLUMN_NAME_MESSAGE_AUTHOR_ID)),
                            cursor.getString(cursor.getColumnIndex(ConversationEntry.COLUMN_NAME_MESSAGE_AUTHOR_NAME)),
                            cursor.getString(cursor.getColumnIndex(ConversationEntry.COLUMN_NAME_MESSAGE_AUTHOR_SUR_NAME)),
                            cursor.getString(cursor.getColumnIndex(ConversationEntry.COLUMN_NAME_MESSAGE_AUTHOR_AVATAR)),
                            cursor.getLong(cursor.getColumnIndex(ConversationEntry.COLUMN_NAME_MESSAGE_AUTHOR_CREATED)),
                            Gender.values()[cursor.getInt(cursor.getColumnIndex(ConversationEntry.COLUMN_NAME_MESSAGE_AUTHOR_GENDER))],
                            cursor.getLong(cursor.getColumnIndex(ConversationEntry.COLUMN_NAME_MESSAGE_AUTHOR_LAST_SEEN)),
                            cursor.getString(cursor.getColumnIndex(ConversationEntry.COLUMN_NAME_MESSAGE_AUTHOR_PLACE)),
                            cursor.getString(cursor.getColumnIndex(ConversationEntry.COLUMN_NAME_MESSAGE_AUTHOR_EDUCATION))
                    ),
                    cursor.getLong(cursor.getColumnIndex(ConversationEntry.COLUMN_NAME_MESSAGE_CREATED))
            )
    )

    private fun createContentValues(conversation: Conversation): ContentValues {
        val values = ContentValues()
        values.put(ConversationEntry.COLUMN_NAME_ID, conversation.mId)
        values.put(ConversationEntry.COLUMN_NAME_TITLE, conversation.mTitle)
        values.put(ConversationEntry.COLUMN_NAME_AVATAR, conversation.mAvatar)
        values.put(ConversationEntry.COLUMN_NAME_UNREAD, conversation.mUnread)

        values.put(ConversationEntry.COLUMN_NAME_MESSAGE_ID, conversation.mDisplayedMessage.mId)
        values.put(ConversationEntry.COLUMN_NAME_MESSAGE_TEXT, conversation.mDisplayedMessage.mText)

        values.put(ConversationEntry.COLUMN_NAME_MESSAGE_AUTHOR_ID, conversation.mDisplayedMessage.mAuthor.mId)
        values.put(ConversationEntry.COLUMN_NAME_MESSAGE_AUTHOR_NAME, conversation.mDisplayedMessage.mAuthor.mName)
        values.put(ConversationEntry.COLUMN_NAME_MESSAGE_AUTHOR_SUR_NAME, conversation.mDisplayedMessage.mAuthor.mSurName)
        values.put(ConversationEntry.COLUMN_NAME_MESSAGE_AUTHOR_AVATAR, conversation.mDisplayedMessage.mAuthor.mAvatar)
        values.put(ConversationEntry.COLUMN_NAME_MESSAGE_AUTHOR_CREATED, conversation.mDisplayedMessage.mAuthor.mCreated)
        values.put(ConversationEntry.COLUMN_NAME_MESSAGE_AUTHOR_GENDER, conversation.mDisplayedMessage.mAuthor.mGender.ordinal)
        values.put(ConversationEntry.COLUMN_NAME_MESSAGE_AUTHOR_LAST_SEEN, conversation.mDisplayedMessage.mAuthor.mLastSeen)
        values.put(ConversationEntry.COLUMN_NAME_MESSAGE_AUTHOR_PLACE, conversation.mDisplayedMessage.mAuthor.mPlace)
        values.put(ConversationEntry.COLUMN_NAME_MESSAGE_AUTHOR_EDUCATION, conversation.mDisplayedMessage.mAuthor.mEducation)

        values.put(ConversationEntry.COLUMN_NAME_MESSAGE_CREATED, conversation.mDisplayedMessage.mCreated)
        return values
    }

    companion object {

        private var sInstance: LocalConversationsDataSource? = null

        fun getInstance(context: Context): LocalConversationsDataSource {
            if (sInstance == null) {
                sInstance = LocalConversationsDataSource(context)
            }

            return sInstance as LocalConversationsDataSource
        }

    }

}