package com.drahanov.muzzchat.ui.chat.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.drahanov.muzzchat.R
import com.drahanov.muzzchat.ui.theme.MuzzChatTheme

@Composable
fun LeaveChatDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.leave_chat_title)) },
        text = { Text(stringResource(R.string.leave_chat_message)) },
        confirmButton = {
            TextButton(onClick = onConfirm) { Text(stringResource(R.string.leave)) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(stringResource(R.string.cancel)) }
        },
    )
}

@Preview
@Composable
private fun LeaveChatDialogPreview() {
    MuzzChatTheme {
        LeaveChatDialog(onConfirm = {}, onDismiss = {})
    }
}
