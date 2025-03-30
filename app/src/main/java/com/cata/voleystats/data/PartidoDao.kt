package com.cata.voleystats.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PartidoDao {
    @Insert
    suspend fun insert(partido: PartidoEntity): Long

    @Query("SELECT * FROM partidos ORDER BY fecha DESC")
    suspend fun getAll(): List<PartidoEntity>

    @Query("DELETE FROM partidos")
    suspend fun deleteAll()

    @Query("DELETE FROM jugadores")
    suspend fun deleteAllJugadores()




}