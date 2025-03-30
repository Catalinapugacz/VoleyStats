package com.cata.voleystats.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun PantallaCargaJugadores(
    equipoLocal: String,
    onGuardarEquipo: (String, List<String>, Boolean) -> Unit, // <-- le pasamos también si se guarda
    onContinuar: () -> Unit,
    onVolver: () -> Unit
) {
    val jugadores = remember {
        mutableStateListOf<TextFieldValue>().apply {
            repeat(6) { add(TextFieldValue("")) }
        }
    }

    var guardarComoFavorito by remember { mutableStateOf(false) }
    var mostrarError by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("Jugadores de $equipoLocal", color = Color.White, style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(modifier = Modifier.weight(1f)) {
                itemsIndexed(jugadores) { index, jugador ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = jugador,
                            onValueChange = { jugadores[index] = it },
                            label = { Text("Jugador ${index + 1}") },
                            modifier = Modifier
                                .weight(1f)
                                .padding(vertical = 4.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.White,
                                unfocusedBorderColor = Color.Gray,
                                cursorColor = Color.White
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(
                            onClick = { if (jugadores.size > 6) jugadores.removeAt(index) },
                            enabled = jugadores.size > 6
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = "Eliminar jugador", tint = Color.Red)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (jugadores.size < 15) {
                OutlinedButton(
                    onClick = { jugadores.add(TextFieldValue("")) },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar jugador")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Agregar jugador", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = guardarComoFavorito,
                    onCheckedChange = { guardarComoFavorito = it },
                    colors = CheckboxDefaults.colors(checkedColor = Color.White)
                )
                Text("Guardar como favorito", color = Color.White)
            }

            if (mostrarError) {
                Text(
                    "Debe completar al menos 6 nombres para continuar",
                    color = Color.Red,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = onVolver) {
                    Text("Volver")
                }
                Button(
                    onClick = {
                        val nombres = jugadores.map { it.text.trim() }.filter { it.isNotEmpty() }
                        if (nombres.size >= 6) {
                            onGuardarEquipo(equipoLocal, nombres, guardarComoFavorito)
                            onContinuar()
                        } else {
                            mostrarError = true
                        }
                    }
                ) {
                    Text("Continuar")
                }
            }
        }
    }
}
