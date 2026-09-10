package com.drahanov.muzzchat.ui.chat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drahanov.muzzchat.domain.model.ChatUser
import com.drahanov.muzzchat.domain.repository.ChatRepository
import com.drahanov.muzzchat.ui.chat.model.ChatUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repository: ChatRepository,
    private val mapper: ChatItemMapper,
) : ViewModel() {

    private val currentUser = MutableStateFlow(ChatUser.ME)

    val uiState: StateFlow<ChatUiState> =
        combine(repository.observeMessages(), currentUser) { messages, user ->
            ChatUiState(items = mapper.map(messages, user.id), currentUser = user)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), ChatUiState(isLoading = true))

    var input by mutableStateOf("")
        private set

    fun onInputChange(text: String) {
        input = text
    }

    fun onSend() {
        val text = input.trim()
        if (text.isEmpty()) return
        input = ""
        viewModelScope.launch { repository.send(currentUser.value.id, text) }
    }

    fun onSwitchUser() = currentUser.update { it.other }
}
