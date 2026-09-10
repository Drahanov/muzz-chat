package com.drahanov.muzzchat.ui.chat

import app.cash.turbine.test
import com.drahanov.muzzchat.domain.model.ChatUser
import com.drahanov.muzzchat.domain.model.Message
import com.drahanov.muzzchat.domain.repository.ChatRepository
import com.drahanov.muzzchat.ui.chat.model.ChatItem
import com.drahanov.muzzchat.util.MainDispatcherRule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.Instant
import java.time.ZoneOffset
import java.util.Locale

class ChatViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository = FakeChatRepository()
    private lateinit var viewModel: ChatViewModel

    @Before
    fun setUp() {
        viewModel = ChatViewModel(repository, ChatItemMapper(ZoneOffset.UTC, Locale.ENGLISH))
    }

    private fun ChatViewModel.type(text: String) {
        onInputChange(text)
        onSend()
    }

    @Test
    fun `sending adds a message and clears the input`() = runTest {
        viewModel.uiState.test {
            assertTrue(awaitItem().items.isEmpty())

            viewModel.type("Hi")

            val row = awaitItem().items.filterIsInstance<ChatItem.MessageRow>().single()
            assertEquals("Hi", row.message.text)
            assertTrue(row.isMine)
            assertEquals("", viewModel.input)
        }
    }

    @Test
    fun `input is trimmed before sending`() = runTest {
        viewModel.type("  Hi  ")

        assertEquals("Hi", repository.messages.value.single().text)
    }

    @Test
    fun `blank input is not sent`() = runTest {
        viewModel.type("   ")

        assertTrue(repository.messages.value.isEmpty())
    }

    @Test
    fun `switching user flips which messages are mine`() = runTest {
        viewModel.uiState.test {
            skipItems(1)
            viewModel.type("Hi")
            assertTrue(awaitItem().items.filterIsInstance<ChatItem.MessageRow>().single().isMine)

            viewModel.onSwitchUser()

            val state = awaitItem()
            assertEquals(ChatUser.SARAH, state.currentUser)
            assertFalse(state.items.filterIsInstance<ChatItem.MessageRow>().single().isMine)
        }
    }

    @Test
    fun `after switching, messages are sent as the other user`() = runTest {
        viewModel.onSwitchUser()
        viewModel.type("Hey")

        assertEquals(ChatUser.SARAH.id, repository.messages.value.single().senderId)
    }

    private class FakeChatRepository : ChatRepository {
        val messages = MutableStateFlow<List<Message>>(emptyList())

        override fun observeMessages() = messages

        override suspend fun send(senderId: Long, text: String) = messages.update {
            it + Message(it.size + 1L, senderId, text, Instant.EPOCH.plusSeconds(it.size.toLong()))
        }
    }
}
