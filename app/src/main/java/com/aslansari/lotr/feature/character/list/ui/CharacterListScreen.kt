package com.aslansari.lotr.feature.character.list.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CharacterListRoute(
    viewModel: CharacterListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    CharacterListScreen(
        uiState = uiState,
    )
}

@Composable
fun CharacterListScreen(
    uiState: CharacterListUIState,
) {
    if (uiState.loading) {
        Dialog(onDismissRequest = {}, properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)) {
            CircularProgressIndicator()
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(uiState.charList) {item: LotrCharacter ->
                LotrCharacterItem(lotrCharacter = item)
            }
        }
    }
}

@Composable
fun LotrCharacterItem(
    lotrCharacter: LotrCharacter,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = lotrCharacter.name,
            style = MaterialTheme.typography.titleMedium
        )
    }
}