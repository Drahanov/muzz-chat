package com.drahanov.muzzchat.domain.model

import java.time.Instant

data class Message(
    val id: Long,
    val senderId: Long,
    val text: String,
    val timestamp: Instant
)