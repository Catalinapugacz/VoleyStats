package com.cata.voleystats

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
import androidx.compose.animation.core.animateIntAsState

@Composable
fun PantallaEstadisticas(
    columnasGanadas: List<String>,
    columnasPerdidas: List<String>,
    equipoLocal: String,
    equipoVisitante: String,
    jugadoresNombres: List<String>,
    onVolver: () -> Unit,
    onPartidoFinalizado: () -> Unit
) {
    val columnas = columnasGanadas + columnasPerdidas

    val jugadores = remember(jugadoresNombres) {
        jugadoresNombres.map { nombre ->
            Jugador(
                nombre = nombre,
                puntosGanados = mutableStateMapOf<String, Int>().apply {
                    columnasGanadas.forEach { this[it] = 0 }
                },
                puntosPerdidos = mutableStateMapOf<String, Int>().apply {
                    columnasPerdidas.forEach { this[it] = 0 }
                }
            )
        }.toMutableStateList()
    }

    val scrollState = rememberScrollState()

    val puntosLocal = jugadores.sumOf { it.puntosGanados.values.sum() }
    val puntosVisitante = jugadores.sumOf { it.puntosPerdidos.values.sum() }
    val setsLocal = remember { mutableIntStateOf(0) }
    val setsVisitante = remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Text(
                text = "$equipoVisitante $puntosVisitante (${setsVisitante.intValue}) - $equipoLocal $puntosLocal (${setsLocal.intValue})",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 28.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(scrollState),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "JUGADOR",
                    modifier = Modifier
                        .width(100.dp)
                        .border(1.dp, Color.DarkGray)
                        .padding(4.dp),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
                columnas.forEach {
                    Text(
                        it,
                        modifier = Modifier
                            .width(100.dp)
                            .border(1.dp, Color.DarkGray)
                            .padding(4.dp),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Divider(color = Color.Gray, thickness = 1.dp)

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .horizontalScroll(scrollState)
            ) {
                items(jugadores) { jugador ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            jugador.nombre,
                            modifier = Modifier
                                .width(100.dp)
                                .border(1.dp, Color.DarkGray)
                                .padding(4.dp),
                            color = Color(0xFF64B5F6),
                            fontWeight = FontWeight.Bold
                        )

                        columnas.forEach { stat ->
                            val statMap = if (jugador.puntosGanados.containsKey(stat)) jugador.puntosGanados else jugador.puntosPerdidos
                            val rawValue = statMap[stat] ?: 0
                            val animatedValue by animateIntAsState(targetValue = rawValue)

                            Box(
                                modifier = Modifier
                                    .width(100.dp)
                                    .border(1.dp, Color.DarkGray)
                                    .padding(4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        "$animatedValue",
                                        color = Color.White,
                                        modifier = Modifier.padding(end = 8.dp)
                                    )
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            "+",
                                            color = Color.Green,
                                            fontSize = 20.sp,
                                            modifier = Modifier
                                                .clickable { statMap[stat] = rawValue + 1 }
                                                .padding(4.dp)
                                        )
                                        Text(
                                            "-",
                                            color = if (rawValue > 0) Color.Red else Color.Gray,
                                            fontSize = 20.sp,
                                            modifier = Modifier
                                                .clickable {
                                                    if (rawValue > 0) statMap[stat] = rawValue - 1
                                                }
                                                .padding(4.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Divider(color = Color.Gray, thickness = 1.dp)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    if (puntosLocal > puntosVisitante) setsLocal.intValue++
                    else if (puntosVisitante > puntosLocal) setsVisitante.intValue++
                }) {
                    Text("Finalizar Set")
                }
                Button(onClick = onPartidoFinalizado) {
                    Text("Finalizar Partido")
                }
            }
        }
    }
}


data class Jugador(
    var nombre: String,
    var puntosGanados: MutableMap<String, Int>,
    var puntosPerdidos: MutableMap<String, Int>
)
