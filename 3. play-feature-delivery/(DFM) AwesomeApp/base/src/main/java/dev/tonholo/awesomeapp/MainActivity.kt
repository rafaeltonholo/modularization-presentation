package dev.tonholo.awesomeapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.SideEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.rememberNavController
import com.google.android.play.core.splitcompat.SplitCompat
import dagger.hilt.android.AndroidEntryPoint
import dev.tonholo.awesomeapp.di.dfm.DynamicFeatureLoaderManager
import dev.tonholo.awesomeapp.navigation.Navigation
import dev.tonholo.awesomeapp.ui.theme.AwesomeAppTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val NAVIGATION_EXTRA = "NAVIGATION_EXTRA"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var dynamicFeatureLoaderManager: DynamicFeatureLoaderManager

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        SplitCompat.installActivity(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(dynamicFeatureLoaderManager) {
            earlyInit()
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    reloadEvents.collectLatest { route ->
                        // hack to reload navigation routes since we can't do it dynamically in Jetpack Compose and
                        // DFM isn't support by Jetpack Compose Navigation library yet.
                        // see: https://issuetracker.google.com/issues/183677219
                        overridePendingTransition(0, 0)
                        finish()
                        startActivity(
                            Intent(this@MainActivity, MainActivity::class.java).apply {
                                flags = Intent.FLAG_ACTIVITY_NO_ANIMATION or
                                        Intent.FLAG_ACTIVITY_NEW_TASK or
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK
                                putExtras(Bundle().apply {
                                    putString(NAVIGATION_EXTRA, route)
                                })
                            }
                        )
                        Runtime.getRuntime().exit(0)
                        overridePendingTransition(0, 0)
                    }
                }
            }
        }

        setContent {
            AwesomeAppTheme {
                val navController = rememberNavController()
                Navigation(
                    navController,
                    dynamicFeatureLoaderManager.destinationManager,
                )

                SideEffect {
                    // hack to reload navigation routes since we can't do it dynamically in Jetpack Compose and
                    // DFM isn't support by Jetpack Compose Navigation library yet.
                    // see: https://issuetracker.google.com/issues/183677219
                    intent?.let {
                        it.getStringExtra(NAVIGATION_EXTRA)?.let { route ->
                            navController.navigate(route)
                        }
                    }
                }
            }
        }
    }
}
