package com.drahanov.muzzchat.di

import android.content.Context
import androidx.room.Room
import com.drahanov.muzzchat.data.RoomChatRepository
import com.drahanov.muzzchat.data.local.ChatDatabase
import com.drahanov.muzzchat.data.local.MessageDao
import com.drahanov.muzzchat.domain.repository.ChatRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindChatRepository(impl: RoomChatRepository): ChatRepository

    companion object {
        @Provides
        @Singleton
        fun provideDatabase(@ApplicationContext context: Context): ChatDatabase =
            Room.databaseBuilder(context, ChatDatabase::class.java, "chat.db").build()

        @Provides
        fun provideMessageDao(db: ChatDatabase): MessageDao = db.messageDao()
    }
}
