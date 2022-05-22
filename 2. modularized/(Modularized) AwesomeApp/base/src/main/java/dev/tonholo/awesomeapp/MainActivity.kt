package dev.tonholo.awesomeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import dev.tonholo.awesomeapp.navigation.DestinationManager
import dev.tonholo.awesomeapp.navigation.Navigation
import dev.tonholo.awesomeapp.ui.theme.AwesomeAppTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var destinationManager: DestinationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AwesomeAppTheme {
                Navigation(destinationManager)
            }
        }
    }
}
