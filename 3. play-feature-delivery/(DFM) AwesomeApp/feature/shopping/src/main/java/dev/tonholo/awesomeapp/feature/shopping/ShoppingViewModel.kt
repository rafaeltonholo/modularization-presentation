package dev.tonholo.awesomeapp.feature.shopping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tonholo.awesomeapp.feature.shopping.usecase.FilterImagesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShoppingViewModel @Inject constructor(
    private val filterImagesUseCase: FilterImagesUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(ShoppingState())
    val state = _state.asStateFlow()

    fun onSearchImage(filter: String) {
        if (state.value.waitingFirstInput) {
            reduce { copy(waitingFirstInput = false) }
        }

        viewModelScope.launch {
            filterImagesUseCase(filter).collect { result ->
                when (result) {
                    is FilterImagesUseCase.Result.Error ->
                        reduce { copy(isSearching = false, images = emptyList()) }
                    FilterImagesUseCase.Result.Loading ->
                        reduce { copy(isSearching = true) }
                    is FilterImagesUseCase.Result.Success ->
                        reduce { copy(isSearching = false, images = result.images) }
                }
            }
        }
    }

    private fun reduce(reducer: ShoppingState.() -> ShoppingState) {
        _state.value = reducer(_state.value)
    }
}
