package com.drahanov.muzzchat

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.drahanov.muzzchat.ui.chat.ChatScreen
import com.drahanov.muzzchat.ui.theme.MuzzChatTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val lightBars = SystemBarStyle.light(scrim = Color.TRANSPARENT, darkScrim = Color.BLACK)
        enableEdgeToEdge(statusBarStyle = lightBars, navigationBarStyle = lightBars)
        setContent {
            MuzzChatTheme {
                ChatScreen(onLeave = ::finish)
            }
        }
    }
}
