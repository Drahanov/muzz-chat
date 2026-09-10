package com.drahanov.muzzchat.ui.chat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.drahanov.muzzchat.ui.theme.MuzzChatTheme

@Composable
fun MessageBubble(
    text: String,
    isMine: Boolean,
    modifier: Modifier = Modifier,
) {
    val colors = MaterialTheme.colorScheme
    val alignment = if (isMine) Alignment.CenterEnd else Alignment.CenterStart

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        contentAlignment = alignment,
    ) {
        Box(Modifier.fillMaxWidth(MAX_WIDTH_FRACTION), contentAlignment = alignment) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge.copy(textDirection = TextDirection.Content),
                color = if (isMine) colors.onPrimary else colors.onSurfaceVariant,
                modifier = Modifier
                    .background(
                        color = if (isMine) colors.primary else colors.surfaceVariant,
                        shape = if (isMine) MineShape else TheirsShape,
                    )
                    .padding(horizontal = 14.dp, vertical = 8.dp),
            )
        }
    }
}

private const val MAX_WIDTH_FRACTION = 0.85f

private val MineShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp, bottomEnd = 0.dp, bottomStart = 12.dp)
private val TheirsShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp, bottomEnd = 12.dp, bottomStart = 0.dp)

@Preview(name = "LTR", showBackground = true, locale = "en")
@Preview(name = "RTL", showBackground = true, locale = "ar")
@Composable
private fun MessageBubblePreview() {
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
