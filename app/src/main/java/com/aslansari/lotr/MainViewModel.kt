package com.aslansari.lotr

import androidx.lifecycle.viewModelScope
import com.aslansari.lotr.architecture.ui.BaseViewModel
import com.aslansari.lotr.architecture.ui.UIEvent
import com.aslansari.lotr.architecture.ui.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class MainViewModel @Inject constructor(

): BaseViewModel<MainUIState, UIEvent>() {

    private val splashDuration = 300.milliseconds

    override fun createInitialState(): MainUIState = MainUIState()

    init {
        viewModelScope.launch {
            delay(splashDuration)
            setState { currentState.copy(loading = false) }
        }
    }
}

data class MainUIState(
    val loading: Boolean = true,
): UIState