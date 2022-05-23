package dev.tonholo.awesomeapp.features.onboard

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tonholo.awesomeapp.R
import dev.tonholo.awesomeapp.data.datastore.DataStoreManager
import dev.tonholo.awesomeapp.data.datastore.extensions.hasPresentedOnboard
import dev.tonholo.awesomeapp.data.datastore.extensions.setHasPresentedOnboard
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
) : ViewModel() {

    val state = mutableStateOf(
        OnboardState(
            listOf(
                OnboardPage(
                    resource = R.drawable.onboard_1,
                    title = "Lorem ipsum dolor sit amet",
                    description = "Ut doloribus error eum doloremque nihil quo consequatur quos aut facere placeat.",
                ),
                OnboardPage(
                    resource = R.drawable.onboard_2,
                    title = "Ea iste dolorem et quia",
                    description = "Voluptas hic alias excepturi est consequatur voluptates sed nesciunt veniam eum accusamus.",
                ),
                OnboardPage(
                    resource = R.drawable.onboard_3,
                    title = "Ab ipsum repudiandae ut",
                    description = "Perferendis ratione ad voluptas error qui earum odio hic internos quia.",
                ),
                OnboardPage(
                    resource = R.drawable.onboard_4,
                    title = "Est galisum voluptas",
                    description = "vel recusandae galisum ad impedit odit a quam voluptatum.",
                ),
            )
        )
    )

    init {
        viewModelScope.launch {
            dataStoreManager.hasPresentedOnboard { hasPresentedOnboard ->
                reduce {
                    if (hasPresentedOnboard) {
                        copy(targetPage = content.size)
                    } else {
                        copy(isReadyToShow = true)
                    }
                }
            }
        }
    }

    fun onNavigateToFeed() {
        viewModelScope.launch {
            dataStoreManager.setHasPresentedOnboard()
        }
    }

    fun onBackButtonClicked() {
        var target = state.value.currentPage - 1
        if (target < 0) target = 0

        reduce { copy(targetPage = target) }
    }

    fun onNextButtonClicked() {
        val target = state.value.currentPage + 1
        reduce { copy(targetPage = target) }
    }

    fun onPageChanged(currentPage: Int) {
        reduce { copy(currentPage = currentPage) }
        updateButtonStates()
    }

    fun onSkipButtonClicked() {
        reduce { copy(targetPage = state.value.content.size) }
    }

    private fun reduce(reducer: OnboardState.() -> OnboardState) {
        state.value = reducer(state.value)
    }

    private fun updateButtonStates() {
        updateSkipButtonState()
        updateBackButtonState()
    }

    private fun updateSkipButtonState() {
        val shouldShowSkipButton = state.value.currentPage < state.value.content.size - 1
        reduce { copy(shouldShowSkipButton = shouldShowSkipButton) }
    }

    private fun updateBackButtonState() {
        val shouldShowBackButton = state.value.currentPage > 0
        reduce { copy(shouldShowBackButton = shouldShowBackButton) }
    }
}
