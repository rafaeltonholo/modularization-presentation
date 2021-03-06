package dev.tonholo.awesomeapp.features.camera.util

import android.content.Context
import android.util.Log
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import dev.tonholo.awesomeapp.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private const val FILE_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
private const val FILE_EXTENSION = ".jpg"

private const val TAG = "CameraUtil"

/**
 * Gets the [ProcessCameraProvider] from context using coroutine.
 */
suspend fun Context.cameraProvider() = suspendCoroutine<ProcessCameraProvider> { continuation ->
    ProcessCameraProvider.getInstance(this).also { cameraProvider ->
        cameraProvider.addListener({
            continuation.resume(cameraProvider.get())
        }, ContextCompat.getMainExecutor(this))
    }
}

fun Context.getPhotoOutputDirectory() =
    File(this.filesDir, getString(R.string.app_name)).apply {
        val mkdirs = mkdirs()
        Log.d(TAG, "getPhotoOutputDirectory: mkdirs = $mkdirs, absoluteFile = $absoluteFile")
    }

fun generatePhotoFilename() =
    SimpleDateFormat(FILE_FORMAT, Locale.ROOT).format(System.currentTimeMillis()) + FILE_EXTENSION
