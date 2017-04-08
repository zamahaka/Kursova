package com.nulp.zamahaka.kursova

import android.content.Context
import com.nulp.zamahaka.kursova.source.ConversationsRepository
import com.nulp.zamahaka.kursova.source.local.LocalConversationsDataSource
import com.nulp.zamahaka.kursova.source.remote.RemoteConversationsDataSource

/**
 * Created by Ura on 02.04.2017.
 */
object Injection {

    @JvmStatic fun provideTasksRepository(context: Context) = ConversationsRepository.getInstance(
            RemoteConversationsDataSource.getInstance(context),
            LocalConversationsDataSource.getInstance(context))
}