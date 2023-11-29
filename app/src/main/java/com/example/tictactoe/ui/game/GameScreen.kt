package com.example.tictactoe.ui.game

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

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

    Board()
}

@Preview(showBackground = true)
@Composable
fun Board() {
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "IdDePartida")
        Text(text = "Es tu turno/Esperando Rival/Turno rival")

        Row {
            GameCell()
            GameCell()
            GameCell()
        }
        Row {
            GameCell()
            GameCell()
            GameCell()
        }
        Row {
            GameCell()
            GameCell()
            GameCell()
        }
    }
}

@Preview
@Composable
fun GameCell() {
    Box(
        modifier = Modifier
            .padding(12.dp)
            .size(64.dp)
            .border(BorderStroke(2.dp, Color.Black)),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "X")
    }
}