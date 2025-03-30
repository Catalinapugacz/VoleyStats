package com.cata.voleystats.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "equipos_favoritos")
data class EquipoFavoritoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val jugadores: List<String>
)
