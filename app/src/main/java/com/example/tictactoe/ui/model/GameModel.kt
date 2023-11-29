package com.example.tictactoe.ui.model

/**
 * player2 tiene que ser nullable porque al crear la partida no se ha unido todavia.
 *
 * Se puede apreciar la gracia de los mapeadores, ya que este modelo es mucho mas limpio que el de
 * data. Casi no tenemos nulables y por lo tanto es mucho mas robusto
 */
data class GameModel(
    val board: List<PlayerType>,
    val player1: PlayerModel,
    val player2: PlayerModel?,
    val playerTurn: PlayerModel,
    val gameId: String
)

data class PlayerModel(val userId: String, val playerType: PlayerType)

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