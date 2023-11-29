package com.example.tictactoe.ui.home

import androidx.lifecycle.ViewModel
import com.example.tictactoe.data.network.FirebaseService
import com.example.tictactoe.data.network.model.GameData
import com.example.tictactoe.data.network.model.PlayerData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val firebaseService: FirebaseService
) : ViewModel() {

    fun onCreateGame() {
        firebaseService.createGame(createNewGame())
    }

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

    fun onJoinGame(it: String) {

    }

}