package com.cata.voleystats.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "equipos")
data class EquipoPersonalizadoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombreEquipo: String,
    val jugadores: List<String>,
    val escudoUri: String? = null // Ruta del archivo local o URI de imagen
)
