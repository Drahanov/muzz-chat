package com.drahanov.muzzchat.ui.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.tools.screenshot.PreviewTest
import com.drahanov.muzzchat.domain.model.ChatUser
import com.drahanov.muzzchat.ui.chat.components.ChatInputBar
import com.drahanov.muzzchat.ui.chat.components.EmptyChat
import com.drahanov.muzzchat.ui.chat.components.MessageBubble
import com.drahanov.muzzchat.ui.chat.components.SectionHeader
import com.drahanov.muzzchat.ui.chat.model.ChatUiState
import com.drahanov.muzzchat.ui.theme.MuzzChatTheme
import java.time.ZoneOffset
import java.util.Locale

@PreviewTest
@Preview(name = "LTR", showBackground = true, locale = "en")
@Preview(name = "RTL", showBackground = true, locale = "ar")
@Composable
fun MessageBubblesScreenshot() {
    MuzzChatTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(vertical = 8.dp),
        ) {
            MessageBubble("Ok cool!", isMine = false)
            MessageBubble("What are you up to today?", isMine = true)
            MessageBubble("مرحبا! كيف حالك؟", isMine = true)
            MessageBubble(
                "Actually just about to go shopping, got any recommendations for a good shoe shop? I'm a fashion disaster",
                isMine = false,
            )
        }
    }
}

@PreviewTest
@Preview(showBackground = true)
@Composable
fun SectionHeaderScreenshot() {
    MuzzChatTheme {
        SectionHeader(day = "Thursday", time = "11:59")
    }
}

@PreviewTest
@Preview(name = "LTR", showBackground = true, locale = "en")
@Preview(name = "RTL", showBackground = true, locale = "ar")
@Composable
fun ChatInputBarScreenshot() {
    MuzzChatTheme {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            ChatInputBar(value = "", onValueChange = {}, onSend = {})
            ChatInputBar(value = "Hey, Sara looks great", onValueChange = {}, onSend = {})
        }
    }
}

@PreviewTest
@Preview(showBackground = true, heightDp = 400)
@Composable
fun EmptyChatScreenshot() {
    MuzzChatTheme {
        EmptyChat(contactName = "Sarah")
    }
}

@PreviewTest
@Preview(device = "id:pixel_5")
@Composable
fun ChatScreenScreenshot() {
    MuzzChatTheme {
        ChatContent(
            state = ChatUiState(
                items = ChatItemMapper(ZoneOffset.UTC, Locale.ENGLISH).map(PreviewMessages, ChatUser.ME.id),
            ),
            input = "Hey, Sara looks great",
            onInputChange = {},
            onSend = {},
            onSwitchUser = {},
            onLeave = {},
        )
    }
}
