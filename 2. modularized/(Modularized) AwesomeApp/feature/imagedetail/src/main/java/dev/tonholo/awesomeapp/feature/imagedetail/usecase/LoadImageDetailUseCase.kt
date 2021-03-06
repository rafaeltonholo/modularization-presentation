package dev.tonholo.awesomeapp.feature.imagedetail.usecase

import android.util.Log
import dev.tonholo.awesomeapp.data.model.UnsplashImage
import dev.tonholo.awesomeapp.data.remote.UnsplashApi
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

private const val TAG = "LoadImageDetailUseCase"
class LoadImageDetailUseCase @Inject constructor(
    private val unsplashApi: UnsplashApi,
) {
    sealed interface Result {
        object Loading : Result
        data class Success(val image: UnsplashImage) : Result
        data class Error(val message: String, val error: Throwable?) : Result
    }

    operator fun invoke(
        id: String,
    ) = flow {
        emit(Result.Loading)

        try {
            val imageDetail = unsplashApi.getDetail(id)
            Log.v(TAG, "Got $imageDetail from remote")
            emit(Result.Success(image = imageDetail))
        } catch (e: Exception) {
            Log.e(TAG, "invoke: Unhandled error", e)
            emit(Result.Error(message = "Unhandled error", e))
        }
    }
}
