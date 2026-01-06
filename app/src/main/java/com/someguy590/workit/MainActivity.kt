package com.someguy590.workit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.NotificationManagerCompat
import com.someguy590.workit.ui.theme.WorkItTheme
import com.someguy590.workit.utils.createNotification
import com.someguy590.workit.utils.createNotificationChannel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel()

        enableEdgeToEdge()
        setContent {
            WorkItTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        {
                            NotificationManagerCompat
                                .from(this)
                                .notify(0, createNotification())
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(
    name: String,
    handleSendNotification: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
        Button(handleSendNotification) {
            Text("Send notification")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WorkItTheme {
        Greeting("Android", {})
    }
}