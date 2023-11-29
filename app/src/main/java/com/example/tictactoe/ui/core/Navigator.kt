package com.example.tictactoe.ui.core

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.tictactoe.ui.game.GameScreen
import com.example.tictactoe.ui.home.HomeScreen

/**
 * En vez de pasarle el navigationController a las pantallas para que implementen la navegación
 * seguimos con el principio de Single Source of Truth, dejando el navigationController en el nivel
 * más alto posible
 */
@Composable
fun ContentWrapper(navigationController: NavHostController) {
    NavHost(
        navController = navigationController,
        startDestination = Routes.Home.route
    ) {
        composable(Routes.Home.route) {
            HomeScreen(navigateToGame = { navigationController.navigate(Routes.Game.route) })
        }
        composable(Routes.Game.route) {
            GameScreen()
        }
    }
}

/** Creamos una sealed class con las rutas para mantener el principio de Single Source of Truth */
sealed class Routes(val route: String) {
    object Home : Routes("home")
    object Game : Routes("game")
}