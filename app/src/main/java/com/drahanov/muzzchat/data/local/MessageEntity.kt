package com.drahanov.muzzchat.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.drahanov.muzzchat.domain.model.Message
import java.time.Instant

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val senderId: Long,
    val text: String,
    val timestamp: Long,
)

fun MessageEntity.toDomain() = Message(
    id = id,
    senderId = senderId,
    text = text,
    timestamp = Instant.ofEpochMilli(timestamp),
)
