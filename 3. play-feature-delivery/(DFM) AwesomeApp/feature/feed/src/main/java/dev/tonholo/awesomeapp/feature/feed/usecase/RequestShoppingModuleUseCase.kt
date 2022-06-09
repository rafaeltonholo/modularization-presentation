package dev.tonholo.awesomeapp.feature.feed.usecase

import android.util.Log
import com.google.android.play.core.ktx.errorCode
import com.google.android.play.core.ktx.requestInstall
import com.google.android.play.core.ktx.requestProgressFlow
import com.google.android.play.core.ktx.status
import com.google.android.play.core.splitinstall.SplitInstallException
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import dev.tonholo.awesomeapp.di.dfm.DynamicFeatureLoaderManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val MODULE_NAME = "shopping"
private const val TAG = "RequestShoppingModuleUs"

class RequestShoppingModuleUseCase @Inject constructor(
    private val splitInstallManager: SplitInstallManager,
    private val dynamicFeatureLoaderManager: DynamicFeatureLoaderManager,
) {
    sealed interface State {
        data class Installed(val reloadRequired: Boolean) : State
        object Preparing : State
        data class Downloading(val totalBytes: Long, val progress: Long) : State
        object Installing : State
        data class Failure(val exception: Throwable) : State
    }

    operator fun invoke(): Flow<State> = flow {
        if (splitInstallManager.installedModules.any { module -> module == MODULE_NAME }) {
            emit(State.Installed(false))
            return@flow
        }

        emit(State.Preparing)
        val flow = splitInstallManager.requestProgressFlow()
            .map { state ->
                when (state.status) {
                    SplitInstallSessionStatus.DOWNLOADING -> {
                        val totalBytes = state.totalBytesToDownload()
                        val progress = state.bytesDownloaded()
                        State.Downloading(totalBytes, progress)
                    }
                    SplitInstallSessionStatus.FAILED -> {
                        State.Failure(
                            Exception("Failed starting installation of $MODULE_NAME. Error: ${state.errorCode}")
                        )
                    }
                    SplitInstallSessionStatus.INSTALLING -> {
                        State.Installing
                    }
                    SplitInstallSessionStatus.INSTALLED -> {
                        reloadFeatureModuleComponent()
                        State.Installed(true)
                    }
                    SplitInstallSessionStatus.DOWNLOADED -> {
                        reloadFeatureModuleComponent()
                        State.Installed(true)
                    }
                    else -> {
                        State.Failure(
                            Exception("Failed starting installation of $MODULE_NAME. Error: ${state.errorCode}")
                        )
                    }
                }
            }

        try {
            splitInstallManager.requestInstall(listOf(MODULE_NAME))
        } catch (e: SplitInstallException) {
            Log.e(TAG, "Failed starting installation of $MODULE_NAME", e)
            emit(State.Failure(e))
        }

        emitAll(flow)
    }

    private suspend fun reloadFeatureModuleComponent() {
        dynamicFeatureLoaderManager.requestReloadDynamicFeatureModule(MODULE_NAME)
    }
}
