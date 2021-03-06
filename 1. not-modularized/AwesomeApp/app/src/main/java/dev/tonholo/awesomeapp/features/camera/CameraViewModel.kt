package dev.tonholo.awesomeapp.features.camera

import android.net.Uri
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.core.net.toFile
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tonholo.awesomeapp.features.camera.util.generatePhotoFilename
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File
import java.util.concurrent.Executors
import javax.inject.Inject

private const val TAG = "CameraViewModel"

@HiltViewModel
class CameraViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(CameraScreenState())
    val state = _state.asStateFlow()

    private val executor = Executors.newSingleThreadExecutor()

    private fun reduce(reducer: CameraScreenState.() -> CameraScreenState) {
        _state.value = reducer(_state.value)
    }

    fun onFlipCameraClick() {
        reduce { copy(isRearCamera = !isRearCamera) }
    }

    fun takePhoto(imageCapture: ImageCapture, outputDir: File) {
        val file = File(
            outputDir,
            generatePhotoFilename(),
        )

        val metadata = ImageCapture.Metadata().apply {
            // Mirror image when using the front camera
            isReversedHorizontal = _state.value.isRearCamera
        }

        val outputOptions = ImageCapture.OutputFileOptions.Builder(file)
            .setMetadata(metadata)
            .build()

        imageCapture.takePicture(outputOptions, executor, object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                reduce { copy(uri = Uri.fromFile(file)) }
            }

            override fun onError(exception: ImageCaptureException) {
                Log.e(TAG, "onError: Error when taking picture", exception)
                reduce { copy(errorMessage = exception.message ?: "Unhandled error") }
            }
        })
    }

    override fun onCleared() {
        executor.shutdown()
    }

    fun discardPhoto(photo: Uri) {
        if (photo.toFile().delete()) {
            reduce { copy(uri = null) }
        } else {
            reduce { copy(errorMessage = "Could not delete photo") }
        }
    }
}
