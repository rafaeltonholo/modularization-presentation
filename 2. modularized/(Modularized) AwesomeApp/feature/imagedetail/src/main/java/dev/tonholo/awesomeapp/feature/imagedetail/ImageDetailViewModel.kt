package dev.tonholo.awesomeapp.feature.imagedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tonholo.awesomeapp.data.remote.fake.FakeUnsplashApi
import dev.tonholo.awesomeapp.di.common.IODispatcher
import dev.tonholo.awesomeapp.feature.imagedetail.usecase.LoadImageDetailUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageDetailViewModel @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val loadImageDetailUseCase: LoadImageDetailUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(ImageDetailState(image = FakeUnsplashApi.fakeImage))
    val state = _state.asStateFlow()

    fun loadDetails(id: String) {
        viewModelScope.launch(ioDispatcher) {
            loadImageDetailUseCase(id).collect { result ->
                when (result) {
                    is LoadImageDetailUseCase.Result.Error ->
                        reduceState { copy(isLoading = false, errorMessage = result.message) }
                    LoadImageDetailUseCase.Result.Loading ->
                        reduceState { copy(isLoading = true) }
                    is LoadImageDetailUseCase.Result.Success ->
                        reduceState { copy(isLoading = false, image = result.image) }
                }
            }
        }
    }

    private fun reduceState(reducer: ImageDetailState.() -> ImageDetailState) {
        _state.value = reducer(_state.value)
    }
}
