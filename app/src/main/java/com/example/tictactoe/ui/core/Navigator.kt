package com.example.tictactoe.ui.core

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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
            HomeScreen(navigateToGame = { gameId, userId, owner ->
                navigationController.navigate(
                    Routes.Game.createRoute(gameId, userId, owner)
                )
            })
        }
        composable(Routes.Game.route,
            arguments = listOf(
                navArgument("gameId") { type = NavType.StringType },
                navArgument("userId") { type = NavType.StringType },
                navArgument("owner") { type = NavType.BoolType }
            )) {
            GameScreen(
                gameId = it.arguments?.getString("gameId").orEmpty(),
                userId = it.arguments?.getString("userId").orEmpty(),
                owner = it.arguments?.getBoolean("owner") ?: false,
                navigateToHome = { navigationController.popBackStack() }
            )
        }
    }
}

/** Creamos una sealed class con las rutas para mantener el principio de Single Source of Truth */
sealed class Routes(val route: String) {

    object Home : Routes("home")

    object Game : Routes("game/{gameId}/{userId}/{owner}") {
        fun createRoute(gameId: String, userId: String, owner: Boolean): String {
            return "game/$gameId/$userId/$owner"
        }
    }

}