package com.example.tictactoe.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = hiltViewModel()) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.weight(2f))
        CreateGame(onCreateGame = { homeViewModel.onCreateGame() })
        Spacer(modifier = Modifier.weight(1f))
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        JoinGame(onJoinGame = { homeViewModel.onJoinGame(it) })
        Spacer(modifier = Modifier.weight(2f))
    }
}

/** En vez de pasar el viewModel pasamos una función lambda, para seguir el principio de
 *  single source of truth */
@Composable
fun CreateGame(onCreateGame: () -> Unit) {
    Button(onClick = { onCreateGame() }) {
        Text("Create game")
    }
}

@Composable
private fun JoinGame(onJoinGame: (String) -> Unit) {
    var text by remember { mutableStateOf("") }

    TextField(value = text, onValueChange = { text = it })

    Button(onClick = { onJoinGame(text) }, enabled = text.isNotEmpty()) {
        Text(text = "Join Game")
    }
}