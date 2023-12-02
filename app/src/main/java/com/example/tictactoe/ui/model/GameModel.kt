package com.example.tictactoe.ui.model

import com.example.tictactoe.data.network.model.GameData
import com.example.tictactoe.data.network.model.PlayerData

/**
 * player2 tiene que ser nullable porque al crear la partida no se ha unido todavia.
 *
 * Se puede apreciar la gracia de los mapeadores, ya que este modelo es mucho mas limpio que el de
 * data. Casi no tenemos nulables y por lo tanto es mucho mas robusto.
 *
 * A los campos del modelo que no tenemos en GameData los tenemos que inicializar para que no nos
 * falle la función toModel de GameData.
 */
data class GameModel(
    val board: List<PlayerType>,
    val player1: PlayerModel,
    val player2: PlayerModel?,
    val playerTurn: PlayerModel,
    val gameId: String,
    val isGameReady: Boolean = false,
    val isMyTurn: Boolean = false
) {
    fun toData(): GameData {
        return GameData(
            board = board.map { it.id },
            gameId = gameId,
            player1 = player1.toData(),
            player2 = player2?.toData(),
            playerTurn = playerTurn.toData(),
        )
    }
}

data class PlayerModel(val userId: String, val playerType: PlayerType) {
    fun toData(): PlayerData {
        return PlayerData(
            userId = userId,
            playerType = playerType.id
        )
    }
}

sealed class PlayerType(val id: Int, val symbol: String) {
    object FirstPlayer : PlayerType(2, "X")
    object SecondPlayer : PlayerType(3, "O")
    object Empty : PlayerType(0, "")

    companion object{
        fun getPlayerById(id: Int?): PlayerType {
            return when(id) {
                FirstPlayer.id -> FirstPlayer
                SecondPlayer.id -> SecondPlayer
                else -> Empty
            }
        }
    }
}