package com.cata.voleystats.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jugadores")
data class JugadorEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val partidoId: Int,  // Clave foránea
    val nombre: String,
    val puntosGanados: Map<String, Int>,
    val puntosPerdidos: Map<String, Int>
)
