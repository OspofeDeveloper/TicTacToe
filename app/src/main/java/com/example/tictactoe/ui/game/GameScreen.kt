package com.example.tictactoe.ui.game

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tictactoe.ui.model.GameModel
import com.example.tictactoe.ui.model.PlayerType

@Composable
fun GameScreen(
    gameViewModel: GameViewModel = hiltViewModel(),
    gameId: String,
    userId: String,
    owner: Boolean
) {
    /** Este metodo en Compose es el equivalente al onCreate en XML */
    LaunchedEffect(true) {
        gameViewModel.joinToGame(gameId, userId, owner)
    }

    val game: GameModel? by gameViewModel.game.collectAsState()

    Board(game) { gameViewModel.onItemSelected(it) }
}

@Composable
fun Board(game: GameModel?, onItemSelected: (Int) -> Unit) {
    if (game == null) return

    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = game.gameId)

        val status = if(game.isGameReady) {
            if(game.isMyTurn) {
                "Tu turno"
            }
            else {
                "Turno Rival"
            }
        } else {
            "Waiting for player 2"
        }

        Text(text = status)

        /**
         * Las lambdas se pasan por parámetro entre {} ya que sino lo que estariamos haciendo en
         * lugar de pasar la función lambda, estariamos ejecutando la instrucción y pasando por
         * parámetro el resultado
         */
        Row {
            GameCell(game.board[0]) { onItemSelected(0) }
            GameCell(game.board[1]) { onItemSelected(1) }
            GameCell(game.board[2]) { onItemSelected(2) }
        }
        Row {
            GameCell(game.board[3]) { onItemSelected(3) }
            GameCell(game.board[4]) { onItemSelected(4) }
            GameCell(game.board[5]) { onItemSelected(5) }
        }
        Row {
            GameCell(game.board[6]) { onItemSelected(6) }
            GameCell(game.board[7]) { onItemSelected(7) }
            GameCell(game.board[8]) { onItemSelected(8) }
        }
    }
}

@Composable
fun GameCell(playerType: PlayerType, onItemSelected: () -> Unit ) {
    Box(
        modifier = Modifier
            .padding(12.dp)
            .size(64.dp)
            .border(BorderStroke(2.dp, Color.Black))
            .clickable { onItemSelected() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = playerType.symbol)
    }
}