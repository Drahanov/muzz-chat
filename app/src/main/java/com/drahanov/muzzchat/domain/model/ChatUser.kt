package com.drahanov.muzzchat.domain.model

enum class ChatUser(val id: Long, val displayName: String) {
    ME(id = 1, displayName = "Alex"),
    SARAH(id = 2, displayName = "Sarah");

    val other: ChatUser
        get() = if (this == ME) SARAH else ME
}
