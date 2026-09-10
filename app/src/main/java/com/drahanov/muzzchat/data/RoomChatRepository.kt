package com.drahanov.muzzchat.data

import com.drahanov.muzzchat.data.local.MessageDao
import com.drahanov.muzzchat.data.local.MessageEntity
import com.drahanov.muzzchat.data.local.toDomain
import com.drahanov.muzzchat.domain.model.Message
import com.drahanov.muzzchat.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomChatRepository @Inject constructor(
    private val dao: MessageDao,
) : ChatRepository {

    override fun observeMessages(): Flow<List<Message>> =
        dao.observeMessages().map { entities -> entities.map { it.toDomain() } }

    override suspend fun send(senderId: Long, text: String) {
        dao.insert(
            MessageEntity(senderId = senderId, text = text, timestamp = System.currentTimeMillis())
        )
    }
}
