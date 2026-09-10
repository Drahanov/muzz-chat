package com.drahanov.muzzchat.ui.chat.model

import com.drahanov.muzzchat.domain.model.Message

sealed interface ChatItem {
    val key: String

    data class SectionHeader(
        val anchorMessageId: Long,
        val day: String,
        val time: String,
    ) : ChatItem {
        override val key = "header_$anchorMessageId"
    }

    data class MessageRow(
        val message: Message,
        val isMine: Boolean,
        val isGroupedWithNext: Boolean,
    ) : ChatItem {
        override val key = "message_${message.id}"
    }
}
