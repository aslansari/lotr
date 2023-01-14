package com.aslansari.lotr.feature.character.list.ui

import androidx.navigation.*
import androidx.navigation.compose.composable

const val characterListRoute = "character_list_route"

fun NavController.navigateToCharacterList(navOptions: NavOptions? = null) {
    this.navigate(characterListRoute, navOptions)
}

fun NavGraphBuilder.characterListScreen(
) {
    composable(
        route = characterListRoute,
    ) {
        CharacterListRoute()
    }
}
