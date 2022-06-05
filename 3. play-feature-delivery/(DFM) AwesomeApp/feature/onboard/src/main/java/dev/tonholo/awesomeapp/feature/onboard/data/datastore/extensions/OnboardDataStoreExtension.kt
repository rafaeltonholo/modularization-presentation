package dev.tonholo.awesomeapp.feature.onboard.data.datastore.extensions

import androidx.datastore.preferences.core.booleanPreferencesKey
import dev.tonholo.awesomeapp.feature.onboard.data.datastore.DataStoreManager

private const val KEY_NAME = "has_presented_onboard"

suspend fun DataStoreManager.setHasPresentedOnboard() {
    val key = booleanPreferencesKey(KEY_NAME)
    store(key, true)
}

suspend fun DataStoreManager.hasPresentedOnboard(onReadFinished: (Boolean) -> Unit) {
    val key = booleanPreferencesKey(KEY_NAME)
    read(key) {
        onReadFinished(this ?: false)
    }

}
