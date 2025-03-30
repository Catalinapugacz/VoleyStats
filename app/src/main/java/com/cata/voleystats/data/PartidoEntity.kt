package com.cata.voleystats.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "partidos")
data class PartidoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val equipoLocal: String,
    val equipoVisitante: String,
    val fecha: Long,  // Timestamp
    val columnasGanadas: List<String>,
    val columnasPerdidas: List<String>,
    val setsLocal: Int,
    val setsVisitante: Int,
    val resultadoFinal: String

)

