package com.drahanov.muzzchat.ui.chat

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.drahanov.muzzchat.domain.model.ChatUser
import com.drahanov.muzzchat.domain.model.Message
import com.drahanov.muzzchat.ui.chat.components.ChatInputBar
import com.drahanov.muzzchat.ui.chat.components.ChatTopBar
import com.drahanov.muzzchat.ui.chat.components.EmptyChat
import com.drahanov.muzzchat.ui.chat.components.LeaveChatDialog
import com.drahanov.muzzchat.ui.chat.components.MessageBubble
import com.drahanov.muzzchat.ui.chat.components.SectionHeader
import com.drahanov.muzzchat.ui.chat.model.ChatItem
import com.drahanov.muzzchat.ui.chat.model.ChatUiState
import com.drahanov.muzzchat.ui.theme.MuzzChatTheme
import java.time.Duration
import java.time.Instant
import java.time.ZoneOffset
import java.util.Locale

@Composable
fun ChatScreen(
    onLeave: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ChatContent(
        state = state,
        input = viewModel.input,
        onInputChange = viewModel::onInputChange,
        onSend = viewModel::onSend,
        onSwitchUser = viewModel::onSwitchUser,
        onLeave = onLeave,
        modifier = modifier,
    )
}

@Composable
private fun ChatContent(
    state: ChatUiState,
    input: String,
    onInputChange: (String) -> Unit,
    onSend: () -> Unit,
    onSwitchUser: () -> Unit,
    onLeave: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var showLeaveDialog by rememberSaveable { mutableStateOf(false) }
    BackHandler { showLeaveDialog = true }

    if (showLeaveDialog) {
        LeaveChatDialog(
            onConfirm = onLeave,
            onDismiss = { showLeaveDialog = false },
        )
    }

    Scaffold(
        topBar = {
            ChatTopBar(
                contactName = state.currentUser.other.displayName,
                onBackClick = { showLeaveDialog = true },
                onSwitchUser = onSwitchUser,
            )
        },
        bottomBar = {
            ChatInputBar(value = input, onValueChange = onInputChange, onSend = onSend)
        },
        modifier = modifier,
    ) { padding ->
        when {
            state.items.isNotEmpty() -> MessageList(items = state.items, modifier = Modifier.padding(padding))
            !state.isLoading -> EmptyChat(
                contactName = state.currentUser.other.displayName,
                modifier = Modifier.padding(padding),
            )
        }
    }
}

@Composable
private fun MessageList(items: List<ChatItem>, modifier: Modifier = Modifier) {
    val listState = rememberLazyListState()
    val newestFirst = remember(items) { items.asReversed() }
    val newestKey = newestFirst.firstOrNull()?.key

    LaunchedEffect(newestKey) {
        if (newestKey != null) listState.animateScrollToItem(0)
    }

    LazyColumn(
        state = listState,
        reverseLayout = true,
        contentPadding = PaddingValues(vertical = 8.dp),
        modifier = modifier.fillMaxSize(),
    ) {
        items(newestFirst, key = { it.key }, contentType = { it::class }) { item ->
            when (item) {
                is ChatItem.SectionHeader -> SectionHeader(day = item.day, time = item.time)
                is ChatItem.MessageRow -> MessageBubble(
                    text = item.message.text,
                    isMine = item.isMine,
                    modifier = Modifier.padding(bottom = if (item.isGroupedWithNext) 2.dp else 8.dp),
                )
            }
        }
    }
}

@Preview
@Composable
private fun ChatContentPreview() {
    val items = remember {
        ChatItemMapper(ZoneOffset.UTC, Locale.ENGLISH).map(PreviewMessages, ChatUser.ME.id)
    }
    MuzzChatTheme {
        ChatContent(
            state = ChatUiState(items = items),
            input = "Hey, Sara looks great",
            onInputChange = {},
            onSend = {},
            onSwitchUser = {},
            onLeave = {},
        )
    }
}

@Preview
@Composable
private fun ChatContentEmptyPreview() {
    MuzzChatTheme {
        ChatContent(
            state = ChatUiState(),
            input = "",
            onInputChange = {},
            onSend = {},
            onSwitchUser = {},
            onLeave = {},
        )
    }
}

private val PreviewStart: Instant = Instant.parse("2026-09-10T11:59:00Z")

private val PreviewMessages = listOf(
    Message(1, ChatUser.SARAH.id, "Wowsa sounds fun", PreviewStart - Duration.ofHours(2)),
    Message(2, ChatUser.SARAH.id, "Yeh for sure that works. What time do you think?", PreviewStart),
    Message(
        3, ChatUser.ME.id,
        "Does 7pm work for you? I've got to go pick up my little brother first from a party",
        PreviewStart.plusSeconds(60),
    ),
    Message(4, ChatUser.SARAH.id, "Ok cool!", PreviewStart.plusSeconds(120)),
    Message(5, ChatUser.ME.id, "What are you up to today?", PreviewStart.plusSeconds(180)),
    Message(6, ChatUser.SARAH.id, "Nothing much", PreviewStart.plusSeconds(240)),
    Message(
        7, ChatUser.SARAH.id,
        "Actually just about to go shopping, got any recommendations for a good shoe shop? I'm a fashion disaster",
        PreviewStart.plusSeconds(250),
    ),
    Message(8, ChatUser.SARAH.id, "The last one went on for hours", PreviewStart.plusSeconds(400)),
)
