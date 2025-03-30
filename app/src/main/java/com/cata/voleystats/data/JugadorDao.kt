package com.cata.voleystats.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface JugadorDao {
    @Insert
    suspend fun insertAll(jugadores: List<JugadorEntity>)

    @Query("SELECT * FROM jugadores WHERE partidoId = :partidoId")
    suspend fun getByPartidoId(partidoId: Int): List<JugadorEntity>
}