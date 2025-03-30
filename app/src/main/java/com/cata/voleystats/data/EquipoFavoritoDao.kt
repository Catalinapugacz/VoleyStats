package com.cata.voleystats.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface EquipoFavoritoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarEquipo(equipo: EquipoFavoritoEntity)

    @Query("SELECT * FROM equipos_favoritos")
    fun obtenerEquipos(): Flow<List<EquipoFavoritoEntity>>

    @Delete
    suspend fun eliminarEquipo(equipo: EquipoFavoritoEntity)
}
