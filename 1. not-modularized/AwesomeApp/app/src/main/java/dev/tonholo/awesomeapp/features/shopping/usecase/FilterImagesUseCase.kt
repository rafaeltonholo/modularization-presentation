package dev.tonholo.awesomeapp.features.shopping.usecase

import android.util.Log
import dev.tonholo.awesomeapp.data.model.UnsplashImage
import dev.tonholo.awesomeapp.data.remote.UnsplashApi
import dev.tonholo.awesomeapp.features.feed.usecase.LoadImagesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

private const val TAG = "FilterImagesUseCase"

class FilterImagesUseCase @Inject constructor(
    private val unsplashApi: UnsplashApi,
) {
    sealed interface Result {
        object Loading : Result
        data class Success(val images: List<UnsplashImage>) : Result
        data class Error(val message: String, val error: Throwable?) : Result
    }

    operator fun invoke(
        query: String,
        page: Int = 1,
        perPage: Int = 10,
        orderBy: String = UnsplashApi.ORDER_BY_LATEST,
    ): Flow<LoadImagesUseCase.Result> = flow {
        emit(LoadImagesUseCase.Result.Loading)

        try {
            val result = unsplashApi.search(
                query,
                page,
                perPage,
                orderBy,
            )
            emit(
                LoadImagesUseCase.Result.Success(
                    result.results,
                )
            )
        } catch (e: Exception) {
            Log.e(TAG, "invoke: Unhandled error", e)
            emit(LoadImagesUseCase.Result.Error(message = "Unhandled error", e))
        }
    }
}
