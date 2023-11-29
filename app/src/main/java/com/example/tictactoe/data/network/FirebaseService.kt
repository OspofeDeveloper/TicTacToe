package com.example.tictactoe.data.network

import android.util.Log
import com.example.tictactoe.data.network.model.GameData
import com.google.firebase.database.DatabaseReference
import javax.inject.Inject

class FirebaseService @Inject constructor(
    private val reference: DatabaseReference
) {

    companion object {
        private const val PATH = "games"
    }

    /**
     * Al crear el nuevo juego necesitamos una id de este para poder recuperarlo y que el
     * player 2 pueda unirse. Para eso recuperamos la clave única del objeto que metemos
     * en firebase y se lo introducimos al parametro gameId de nuestro modelo data.
     *
     * Como nuestro gameId es val no podemos modificarlo, entonces en vez de cambiar el parámetro
     * a var que es una mala práctica lo modificamos usando el copy de nuestro gameData. De esta
     * forma mantenemos la inmutabilidad de nuestros modelos
     *
     * El recuperar la key únca que se crea en la base de datos es muy útil en cualquier aplicación
     * que necesitemos recuperar un objeto en específico.
     *
     * Ya por último podemos devolver el id de la nueva partida para que cuando naveguemos a GameScreen
     * consumamos esa partida y no otra.
     *
     * Una ultima cosa a recordar es que para debugar podemos añadir listeners a nuestra operación
     * de firebase con tal de ver que es lo que falla (.addOnDailureListener por ejemplo)
     */
    fun createGame(gameData: GameData): String {
        val gameReference = reference.child(PATH).push()
        val key = gameReference.key
        val newGame = gameData.copy(gameId = key)

        gameReference.setValue(newGame)

        return newGame.gameId.orEmpty()
    }
}