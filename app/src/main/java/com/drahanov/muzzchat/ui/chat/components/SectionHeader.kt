package com.drahanov.muzzchat.ui.chat.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.drahanov.muzzchat.ui.theme.MuzzChatTheme

@Composable
fun SectionHeader(
    day: String,
    time: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = buildAnnotatedString {
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append(day) }
            append(" $time")
        },
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.outlineVariant,
        textAlign = TextAlign.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
    )
}

@Preview(showBackground = true)
@Composable
private fun SectionHeaderPreview() {
    MuzzChatTheme {
        SectionHeader(day = "Thursday", time = "11:59")
    }
}
