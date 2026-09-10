package com.drahanov.muzzchat.domain.repository

import com.drahanov.muzzchat.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun observeMessages(): Flow<List<Message>>

    suspend fun send(senderId: Long, text: String)
}
