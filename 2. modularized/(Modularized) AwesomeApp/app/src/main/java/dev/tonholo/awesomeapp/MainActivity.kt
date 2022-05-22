package dev.tonholo.awesomeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import dev.tonholo.awesomeapp.navigation.Navigation
import dev.tonholo.awesomeapp.ui.theme.AwesomeAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AwesomeAppTheme {
                Navigation()
            }
        }
    }
}
