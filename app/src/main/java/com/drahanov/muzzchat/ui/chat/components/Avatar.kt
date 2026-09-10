package com.drahanov.muzzchat.ui.chat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Avatar(
    name: String,
    modifier: Modifier = Modifier,
    size: Dp = 36.dp,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(size)
            .background(MaterialTheme.colorScheme.primary, CircleShape),
    ) {
        Text(
            text = name.take(1),
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = with(LocalDensity.current) { (size * 0.45f).toSp() },
            fontWeight = FontWeight.Bold,
        )
    }
}
