package com.drahanov.muzzchat.ui.chat.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.drahanov.muzzchat.R
import com.drahanov.muzzchat.ui.theme.MuzzChatTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTopBar(
    contactName: String,
    onBackClick: () -> Unit,
    onSwitchUser: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(shadowElevation = 4.dp, modifier = modifier) {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = stringResource(R.string.back),
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(36.dp),
                    )
                }
            },
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Avatar(contactName)
                    Text(
                        text = contactName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                    )
                }
            },
            actions = { MoreMenu(contactName, onSwitchUser) },
        )
    }
}

@Composable
private fun MoreMenu(contactName: String, onSwitchUser: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(
                painter = painterResource(R.drawable.ic_more_horiz),
                contentDescription = stringResource(R.string.more_options),
                tint = MaterialTheme.colorScheme.outline,
            )
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = { Text(stringResource(R.string.switch_to, contactName)) },
                onClick = {
                    expanded = false
                    onSwitchUser()
                },
            )
        }
    }
}

@Preview(name = "LTR", locale = "en")
@Preview(name = "RTL", locale = "ar")
@Composable
private fun ChatTopBarPreview() {
    MuzzChatTheme {
        ChatTopBar(contactName = "Sarah", onBackClick = {}, onSwitchUser = {})
    }
}
