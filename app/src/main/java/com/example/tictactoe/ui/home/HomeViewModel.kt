package com.example.tictactoe.ui.home

import androidx.lifecycle.ViewModel
import com.example.tictactoe.data.network.FirebaseService
import com.example.tictactoe.data.network.model.GameData
import com.example.tictactoe.data.network.model.PlayerData
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val firebaseService: FirebaseService
) : ViewModel() {

    /** Implementamos aqui la navegación una vez recuperamos los campos necesarios */
    fun onCreateGame(navigateToGame: (String, String, Boolean) -> Unit) {
        val game = createNewGame()
        val gameId = firebaseService.createGame(game)
        val userId = game.player1?.userId.orEmpty()
        val owner = true

        navigateToGame(gameId, userId, owner)
    }

    fun onJoinGame(gameId: String, navigateToGame: (String, String, Boolean) -> Unit) {
        val owner = false
        navigateToGame(gameId, createUserId(), owner)
    }

    private fun createUserId(): String {
        return Calendar.getInstance().timeInMillis.hashCode().toString()
    }

    /**
     * Como el jugador que crea la partida siempre va a ser el jugador 1, creamos ese jugador
     * a partir de currentPlayer, indicando que el jugador es el jugador 1 y posteriormente guardar
     * en nuestro modelo de Data esa info en player1 y playerTurn, ya que el jugador 1 siempre va
     * a empezar primero
     */
    private fun createNewGame(): GameData {
        val currentPlayer = PlayerData(playerType = 1)

        return GameData(
            /** Creamos una lista con las 9 celdas */
            board = List(9) { 0 },
            player1 = currentPlayer,
            playerTurn = currentPlayer,
            player2 = null
        )
    }
}