package com.nulp.zamahaka.kursova.source.local.database.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.nulp.zamahaka.kursova.source.local.database.entry.ConversationEntry

/**
 * Created by Ura on 08.04.2017.
 */
class ConversationDbHelper(context: Context)
    : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        throw UnsupportedOperationException("not implemented")
    }

    companion object {
        val DATABASE_VERSION = 1

        val DATABASE_NAME = "Conversations.db"

        val SQL_CREATE_ENTRIES = "CREATE TABLE ${ConversationEntry.TABLE_NAME} (" +
                "${ConversationEntry.COLUMN_NAME_ID} INTEGER," +
                "${ConversationEntry.COLUMN_NAME_TITLE} TEXT," +
                "${ConversationEntry.COLUMN_NAME_AVATAR} TEXT," +
                "${ConversationEntry.COLUMN_NAME_UNREAD} INTEGER," +
                "${ConversationEntry.COLUMN_NAME_MESSAGE_ID} INTEGER," +
                "${ConversationEntry.COLUMN_NAME_MESSAGE_TEXT} TEXT," +
                "${ConversationEntry.COLUMN_NAME_MESSAGE_AUTHOR_ID} INTEGER," +
                "${ConversationEntry.COLUMN_NAME_MESSAGE_AUTHOR_NAME} TEXT," +
                "${ConversationEntry.COLUMN_NAME_MESSAGE_AUTHOR_SUR_NAME} TEXT," +
                "${ConversationEntry.COLUMN_NAME_MESSAGE_AUTHOR_AVATAR} TEXT," +
                "${ConversationEntry.COLUMN_NAME_MESSAGE_AUTHOR_CREATED} LONG," +
                "${ConversationEntry.COLUMN_NAME_MESSAGE_AUTHOR_GENDER} INTEGER," +
                "${ConversationEntry.COLUMN_NAME_MESSAGE_AUTHOR_LAST_SEEN} LONG," +
                "${ConversationEntry.COLUMN_NAME_MESSAGE_AUTHOR_PLACE} TEXT," +
                "${ConversationEntry.COLUMN_NAME_MESSAGE_AUTHOR_EDUCATION} TEXT," +
                "${ConversationEntry.COLUMN_NAME_MESSAGE_CREATED} LONG" +
                " )"
    }

}