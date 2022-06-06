package dev.tonholo.awesomeapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.android.play.core.splitcompat.SplitCompat
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import dev.tonholo.awesomeapp.di.DynamicFeatureComponent
import dev.tonholo.awesomeapp.di.DynamicFeatureLoader
import dev.tonholo.awesomeapp.navigation.DestinationManager
import dev.tonholo.awesomeapp.navigation.Navigation
import dev.tonholo.awesomeapp.ui.theme.AwesomeAppTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var destinationManager: DestinationManager

    @Inject
    lateinit var dynamicFeatureLoaders: Set<@JvmSuppressWildcards DynamicFeatureLoader>

    private lateinit var dynamicFeatureComponents: Set<DynamicFeatureComponent>

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        SplitCompat.installActivity(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeDynamicFeatureComponents()

        destinationManager.attachDynamicFeatureModuleDestinations(
            dynamicFeatureComponents.flatMap { dfm -> dfm.getFeatureRoutes() }.toSet()
        )
        setContent {
            AwesomeAppTheme {
                Navigation(destinationManager)
            }
        }
    }

    private fun initializeDynamicFeatureComponents() {
        dynamicFeatureComponents = dynamicFeatureLoaders.map { dfm ->
            dfm.load(EntryPointAccessors.fromApplication(this))
        }.toSet()
    }
}
