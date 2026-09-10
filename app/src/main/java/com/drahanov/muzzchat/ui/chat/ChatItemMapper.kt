package com.drahanov.muzzchat.ui.chat

import com.drahanov.muzzchat.domain.model.Message
import com.drahanov.muzzchat.ui.chat.model.ChatItem
import java.time.Duration
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

class ChatItemMapper(zoneId: ZoneId, locale: Locale) {
    @Inject
    constructor() : this(ZoneId.systemDefault(), Locale.getDefault())

    private val dayFormatter = DateTimeFormatter.ofPattern(DAY_PATTERN, locale).withZone(zoneId)
    private val timeFormatter = DateTimeFormatter.ofPattern(TIME_PATTERN, locale).withZone(zoneId)

    fun map(messages: List<Message>, currentUserId: Long): List<ChatItem> = buildList {
        messages.forEachIndexed { i, message ->
            val prev = messages.getOrNull(i - 1)
            val next = messages.getOrNull(i + 1)

            if (prev == null || gap(prev, message) > SECTION_GAP) {
                add(
                    ChatItem.SectionHeader(
                        anchorMessageId = message.id,
                        day = dayFormatter.format(message.timestamp),
                        time = timeFormatter.format(message.timestamp),
                    )
                )
            }
            add(
                ChatItem.MessageRow(
                    message = message,
                    isMine = message.senderId == currentUserId,
                    isGroupedWithNext = next != null &&
                        next.senderId == message.senderId &&
                        gap(message, next) < GROUP_GAP,
                )
            )
        }
    }

    private fun gap(from: Message, to: Message): Duration =
        Duration.between(from.timestamp, to.timestamp)

    private companion object {
        private val SECTION_GAP: Duration = Duration.ofHours(1)
        private val GROUP_GAP: Duration = Duration.ofSeconds(20)
        private const val DAY_PATTERN: String = "EEEE"
        private const val TIME_PATTERN: String = "HH:mm"
    }
}
