package com.drahanov.muzzchat.ui.chat.model

import com.drahanov.muzzchat.domain.model.ChatUser

data class ChatUiState(
    val items: List<ChatItem> = emptyList(),
    val currentUser: ChatUser = ChatUser.ME,
    val isLoading: Boolean = false,
)
