package dev.tonholo.awesomeapp.feature.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tonholo.awesomeapp.data.model.UnsplashImage
import dev.tonholo.awesomeapp.di.common.IODispatcher
import dev.tonholo.awesomeapp.feature.feed.usecase.LoadImagesUseCase
import dev.tonholo.awesomeapp.navigation.Routes
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    @IODispatcher ioDispatcher: CoroutineDispatcher,
    loadImagesUseCase: LoadImagesUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(FeedState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(ioDispatcher) {
            loadImagesUseCase().collect { state ->
                when (state) {
                    is LoadImagesUseCase.Result.Error ->
                        reduceState {
                            copy(isLoading = false, hasError = true, errorMessage = state.message)
                        }
                    LoadImagesUseCase.Result.Loading -> reduceState {
                        copy(isLoading = true)
                    }
                    is LoadImagesUseCase.Result.Success -> reduceState {
                        copy(isLoading = false, images = state.images)
                    }
                }
            }
        }
    }

    private fun reduceState(reducer: FeedState.() -> FeedState) {
        _state.value = reducer(_state.value)
    }

    fun onFabClicked() {
        reduceState { copy(destinationTarget = Routes.Camera()) }
    }

    fun onImageClicked(image: UnsplashImage) {
        reduceState { copy(destinationTarget = Routes.ImageDetails(image.id)) }
    }

    fun onImageFavClicked(image: UnsplashImage) {

    }

    fun onNavigateTriggered() {
        reduceState { copy(destinationTarget = null) }
    }
}
