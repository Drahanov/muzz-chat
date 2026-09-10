package com.drahanov.muzzchat.ui.chat

import com.drahanov.muzzchat.domain.model.Message
import com.drahanov.muzzchat.ui.chat.model.ChatItem
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.Instant
import java.time.ZoneOffset
import java.util.Locale

class ChatItemMapperTest {

    private val mapper = ChatItemMapper(ZoneOffset.UTC, Locale.ENGLISH)
    private val start = Instant.parse("2026-09-10T11:59:00Z") // a Thursday

    private fun message(id: Long, senderId: Long = ME, atSecond: Long = 0) =
        Message(id, senderId, "text $id", start.plusSeconds(atSecond))

    private fun map(vararg messages: Message) = mapper.map(messages.toList(), currentUserId = ME)

    private fun List<ChatItem>.headers() = filterIsInstance<ChatItem.SectionHeader>()
    private fun List<ChatItem>.rows() = filterIsInstance<ChatItem.MessageRow>()

    @Test
    fun `empty list maps to no items`() {
        assertEquals(emptyList<ChatItem>(), map())
    }

    @Test
    fun `first message gets a section header`() {
        val items = map(message(1))

        assertEquals(ChatItem.SectionHeader(1, "Thursday", "11:59"), items.first())
    }

    @Test
    fun `gap of exactly one hour does not start a new section`() {
        val items = map(message(1), message(2, atSecond = HOUR))

        assertEquals(1, items.headers().size)
    }

    @Test
    fun `gap over one hour starts a new section above that message`() {
        val first = message(1)
        val second = message(2, atSecond = HOUR + 1)

        assertEquals(
            listOf(
                ChatItem.SectionHeader(1, "Thursday", "11:59"),
                ChatItem.MessageRow(first, isMine = true, isGroupedWithNext = false),
                ChatItem.SectionHeader(2, "Thursday", "12:59"),
                ChatItem.MessageRow(second, isMine = true, isGroupedWithNext = false),
            ),
            map(first, second),
        )
    }

    @Test
    fun `same sender within 20 seconds is grouped`() {
        val rows = map(message(1), message(2, atSecond = 19)).rows()

        assertTrue(rows[0].isGroupedWithNext)
    }

    @Test
    fun `same sender exactly 20 seconds later is not grouped`() {
        val rows = map(message(1), message(2, atSecond = 20)).rows()

        assertFalse(rows[0].isGroupedWithNext)
    }

    @Test
    fun `different sender is never grouped`() {
        val rows = map(message(1), message(2, senderId = OTHER, atSecond = 5)).rows()

        assertFalse(rows[0].isGroupedWithNext)
    }

    @Test
    fun `last message is never grouped`() {
        val rows = map(message(1), message(2, atSecond = 5)).rows()

        assertFalse(rows.last().isGroupedWithNext)
    }

    @Test
    fun `isMine reflects the current user`() {
        val rows = map(message(1, senderId = ME), message(2, senderId = OTHER)).rows()

        assertTrue(rows[0].isMine)
        assertFalse(rows[1].isMine)
    }

    @Test
    fun `switching current user flips isMine`() {
        val messages = listOf(message(1, senderId = ME), message(2, senderId = OTHER))

        val rows = mapper.map(messages, currentUserId = OTHER).rows()

        assertFalse(rows[0].isMine)
        assertTrue(rows[1].isMine)
    }

    private companion object {
        const val ME = 1L
        const val OTHER = 2L
        const val HOUR = 3600L
    }
}
