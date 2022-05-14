package dev.tonholo.awesomeapp.features.feed.usecaseimport dev.tonholo.awesomeapp.data.model.UnsplashImageimport dev.tonholo.awesomeapp.data.remote.UnsplashApiimport kotlinx.coroutines.flow.Flowimport kotlinx.coroutines.flow.flowimport javax.inject.Injectclass LoadImagesUseCase @Inject constructor(    private val unsplashApi: UnsplashApi,) {    sealed interface Result {        object Loading : Result        data class Success(val images: List<UnsplashImage>) : Result        data class Error(val message: String, val error: Throwable?) : Result    }    operator fun invoke(        page: Int = 1,        perPage: Int = 10,        orderBy: String = UnsplashApi.ORDER_BY_LATEST,    ): Flow<Result> = flow {        emit(Result.Loading)        try {            emit(                Result.Success(                    unsplashApi.getPhotos(                        page,                        perPage,                        orderBy,                    )                )            )        } catch (e: Exception) {            emit(Result.Error(message = "Unhandled error", e))        }    }}