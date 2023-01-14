package com.aslansari.lotr.feature.character.list.ui

import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.aslansari.lotr.architecture.domain.Resource
import com.aslansari.lotr.architecture.ui.BaseViewModel
import com.aslansari.lotr.architecture.ui.UIEvent
import com.aslansari.lotr.architecture.ui.UIState
import com.aslansari.lotr.feature.character.data.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val characterRepository: CharacterRepository
) : BaseViewModel<CharacterListUIState, CharacterListUIEvent>() {

    override fun createInitialState(): CharacterListUIState = CharacterListUIState()

    init {
        viewModelScope.launch {
            characterRepository.getCharacterList()
                .collect { state ->
                    when (state) {
                        Resource.Loading -> {
                            setState {
                                currentState.copy(loading = true)
                            }
                        }
                        is Resource.Error -> {
                            setState {
                                currentState.copy(loading = false)
                            }
                        }
                        is Resource.Success -> {
                            setState {
                                currentState.copy(loading = false, charList = state.data)
                            }
                        }
                    }
                }
        }
    }
}

@Stable
data class CharacterListUIState(
    val loading: Boolean = false,
    val charList: List<LotrCharacter> = listOf(),
) : UIState

sealed interface CharacterListUIEvent : UIEvent