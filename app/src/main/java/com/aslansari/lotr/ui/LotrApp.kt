package com.aslansari.lotr.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.aslansari.lotr.feature.character.list.ui.characterListRoute
import com.aslansari.lotr.feature.character.list.ui.characterListScreen

@Composable
fun LotrApp(

) {
    val navController: NavHostController = rememberNavController()

    LotrNavHost(
        navController = navController,
        onBackClick = { navController.navigateUp() },
    )
}

@Composable
fun LotrNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onBackClick: () -> Unit,
    startDestination: String = characterListRoute,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        characterListScreen()
    }
}