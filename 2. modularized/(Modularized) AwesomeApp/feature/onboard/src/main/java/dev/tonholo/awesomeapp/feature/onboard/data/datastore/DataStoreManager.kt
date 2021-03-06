package dev.tonholo.awesomeapp.feature.onboard.data.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private const val STORE_NAME = "awesome_app_preferences_datastore"
private const val TAG = "DataStoreManager"

class DataStoreManager @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(STORE_NAME)

    suspend fun <T> store(key: Preferences.Key<T>, value: T) {
        context.dataStore.edit {
            it[key] = value
        }
    }

    suspend fun <T> read(key: Preferences.Key<T>, onReadFinished: T?.() -> Unit) {
        context.dataStore.data
            .catch {
                if (it is IOException) {
                    Log.e(TAG, "read: IOException thrown", it)
                    emit(emptyPreferences())
                } else {
                    throw it
                }
            }
            .map { it[key] }
            .collect(onReadFinished)
    }
}
