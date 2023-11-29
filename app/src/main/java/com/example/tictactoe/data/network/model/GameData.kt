package com.example.tictactoe.data.network.model

import com.example.tictactoe.ui.model.GameModel
import com.example.tictactoe.ui.model.PlayerModel
import com.example.tictactoe.ui.model.PlayerType
import java.util.Calendar

/** En este caso tendremos este modelo generico para data, es decir, en lugar de tener 2
 *  modelos diferentes Response para recibir datos de Firebase y Dto para mandarlos, como la
 *  estructura de los dos modelos va a ser la misma en este caso, podemos encapsular ambas en
 *  una clase mas generica. Ambas sirven pero lo mas estricto seria tener dos separadas */
data class GameData(
    val board: List<Int?>? = null,
    val gameId: String? = null,
    val player1: PlayerData? = null,
    val player2: PlayerData? = null,
    val playerTurn: PlayerData? = null
) {
    fun toModel(): GameModel {
        return GameModel(
            board = board?.map { PlayerType.getPlayerById(it) } ?: mutableListOf(),
            gameId = gameId.orEmpty(),
            player1 = player1!!.toModel(),
            player2 = player2?.toModel(),
            playerTurn = playerTurn!!.toModel()
        )
    }
}

/**
 *  El playerType va a ser un Int sacado de una enum class, por eso board es una lista de Int,
 *  porque en vez de poner si es un circulo o una X vamos a indicar que jugador es el que tiene
 *  el turno
 *
 *  Si queremos crear una variable que sea un Id único, un truco que podemos usar es crear ese
 *  valor único a partir de la fecha exacta en milisegundos que se crea el objeto. Como es en
 *  milisegundos aunque los creemos a la vez nunca llegan a ser iguales del todo
 */
data class PlayerData(
    val userId: String? = Calendar.getInstance().timeInMillis.hashCode().toString(),
    val playerType: Int? = null
) {
    fun toModel(): PlayerModel {
        return PlayerModel(userId!!, PlayerType.getPlayerById(playerType))
    }
}