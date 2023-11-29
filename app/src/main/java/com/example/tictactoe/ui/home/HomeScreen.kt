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

@Composable
fun HomeScreen() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.weight(2f))
        CreateGame()
        Spacer(modifier = Modifier.weight(1f))
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        JoinGame()
        Spacer(modifier = Modifier.weight(2f))
    }
}

@Composable
fun CreateGame() {
    Button(onClick = { /*TODO*/ }) {
        Text("Create game")
    }
}

@Composable
private fun JoinGame() {
    var text by remember{ mutableStateOf("")}

    TextField(value = text, onValueChange = { text = it })

    Button(onClick = { }, enabled = text.isNotEmpty()) {
        Text(text = "Join Game")
    }
}