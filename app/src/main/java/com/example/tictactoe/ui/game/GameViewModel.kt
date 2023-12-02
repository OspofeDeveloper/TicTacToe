package com.example.tictactoe.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tictactoe.data.network.FirebaseService
import com.example.tictactoe.ui.model.GameModel
import com.example.tictactoe.ui.model.PlayerModel
import com.example.tictactoe.ui.model.PlayerType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val firebaseService: FirebaseService
) : ViewModel() {

    private lateinit var userId: String

    private var _game = MutableStateFlow<GameModel?>(null)
    val game: StateFlow<GameModel?> = _game

    fun joinToGame(gameId: String, userId: String, owner: Boolean) {
        this.userId = userId
        if (owner) {
            join(gameId)
        } else {
            joinGameLikeGuest(gameId)
        }
    }

    /**
     *  Comprobamos que al unirme a la partida como invitado por primera vez añadimos
     *  el player2 en firebase.
     *
     *  En este caso usamos el .take(1) para ver como podemos implementar el que solamente recojamos
     *  el resultado de un flow una unica vez, pero realmente podriamos haber declarado un Boolean
     *  isFirstTime a false y poner if(!isFirstTime) { isFirstTime = true {Codigo correspondiente}}
     */
    private fun joinGameLikeGuest(gameId: String) {
        viewModelScope.launch {

            firebaseService.joinToGame(gameId).take(1).collect {
                var result = it
                if (result != null) {
                    result = result.copy(player2 = PlayerModel(userId, PlayerType.SecondPlayer))
                    firebaseService.updateGame(result.toData())
                }
            }

            join(gameId)
        }
    }

    private fun join(gameId: String) {
        viewModelScope.launch {
            firebaseService.joinToGame(gameId).collect {
                val result = it?.copy(isGameReady = it.player2 != null, isMyTurn = isMyTurn())
                _game.value = result
            }
        }
    }

    private fun isMyTurn(): Boolean {
        return game.value?.playerTurn?.userId == userId
    }

    fun onItemSelected(position: Int) {
        val currentGame = _game.value ?: return
        if (currentGame.isGameReady && currentGame.board[position] == PlayerType.Empty && isMyTurn()) {
            viewModelScope.launch {
                val newBoard = currentGame.board.toMutableList()
                newBoard[position] = getPlayer()

                firebaseService.updateGame(
                    currentGame.copy(board = newBoard, playerTurn = getPlayerTurn()!!).toData()
                )
            }
        }
    }


    private fun getPlayer(): PlayerType {
        return when {
            (game.value?.player1?.userId == userId) -> PlayerType.FirstPlayer
            (game.value?.player2?.userId == userId) -> PlayerType.SecondPlayer
            else -> PlayerType.Empty
        }
    }

    private fun getPlayerTurn(): PlayerModel? {
        return if (game.value?.player1?.userId == userId) game.value?.player2 else game.value?.player1
    }

}