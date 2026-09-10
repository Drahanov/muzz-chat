package com.drahanov.muzzchat.ui.chat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.drahanov.muzzchat.R
import com.drahanov.muzzchat.ui.theme.MuzzChatTheme
import com.drahanov.muzzchat.ui.theme.MuzzPeach
import com.drahanov.muzzchat.ui.theme.MuzzPink

@Composable
fun ChatInputBar(
    value: String,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = MaterialTheme.colorScheme

    Surface(shadowElevation = 8.dp, modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .navigationBarsPadding()
                .imePadding()
                .padding(horizontal = 16.dp, vertical = 10.dp),
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    color = colors.onSurface,
                    textDirection = TextDirection.Content,
                ),
                cursorBrush = SolidColor(colors.primary),
                maxLines = 4,
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                decorationBox = { innerTextField ->
                    Box {
                        if (value.isEmpty()) {
                            Text(
                                text = stringResource(R.string.message_hint),
                                style = MaterialTheme.typography.bodyLarge,
                                color = colors.outlineVariant,
                            )
                        }
                        innerTextField()
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .border(
                        width = 1.dp,
                        color = if (value.isEmpty()) colors.outline else colors.primary.copy(alpha = 0.6f),
                        shape = FieldShape,
                    )
                    .padding(horizontal = 16.dp, vertical = 10.dp),
            )
            SendButton(enabled = value.isNotBlank(), onClick = onSend)
        }
    }
}

@Composable
private fun SendButton(enabled: Boolean, onClick: () -> Unit) {
    IconButton(onClick = onClick, enabled = enabled) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(SendGradient, CircleShape, alpha = if (enabled) 1f else 0.5f),
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = stringResource(R.string.send),
                tint = Color.White,
                modifier = Modifier.size(20.dp),
            )
        }
    }
}

private val FieldShape = RoundedCornerShape(20.dp)
private val SendGradient = Brush.linearGradient(listOf(MuzzPink, MuzzPeach))

@Preview(name = "LTR", locale = "en")
@Preview(name = "RTL", locale = "ar")
@Composable
private fun ChatInputBarPreview() {
    MuzzChatTheme {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            ChatInputBar(value = "", onValueChange = {}, onSend = {})
            ChatInputBar(value = "Hey, Sara looks great", onValueChange = {}, onSend = {})
        }
    }
}
