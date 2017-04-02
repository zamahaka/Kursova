package com.nulp.zamahaka.kursova

import android.content.Context
import com.nulp.zamahaka.kursova.source.ConversationsRepository

/**
 * Created by Ura on 02.04.2017.
 */
object Injection {

    @JvmStatic fun provideTasksRepository(context: Context) = ConversationsRepository.getInstance(
            FakeTasksRemoteDataSource.instance,
            TasksLocalDataSource.getInstance(context))
}