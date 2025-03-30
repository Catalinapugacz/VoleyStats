package com.cata.voleystats.data

import androidx.room.*

@Dao
interface EquipoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarEquipo(equipo: EquipoPersonalizadoEntity)

    @Query("SELECT * FROM equipos")
    suspend fun obtenerTodosLosEquipos(): List<EquipoPersonalizadoEntity>

    @Query("SELECT * FROM equipos WHERE nombreEquipo = :nombre")
    suspend fun obtenerEquipoPorNombre(nombre: String): EquipoPersonalizadoEntity?

    @Delete
    suspend fun eliminarEquipo(equipo: EquipoPersonalizadoEntity)

    @Query("DELETE FROM equipos")
    suspend fun eliminarTodosLosEquipos()
}
