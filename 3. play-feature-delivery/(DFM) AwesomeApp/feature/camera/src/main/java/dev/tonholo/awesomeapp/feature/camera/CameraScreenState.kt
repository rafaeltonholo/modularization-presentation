package dev.tonholo.awesomeapp.feature.camera

import android.net.Uri

data class CameraScreenState(
    val uri: Uri? = null,
    val isRearCamera: Boolean = false,
    val errorMessage: String? = null,
)
