package com.cata.voleystats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PantallaPersonalizacion(
    columnasGanadas: MutableList<String>,
    columnasPerdidas: MutableList<String>,
    equipoLocal: MutableState<String>,
    equipoVisitante: MutableState<String>,
    onFinalizar: () -> Unit
) {
    val scrollState = rememberScrollState()

    val defaultGanadas = listOf("Saque", "Remate", "Toque")
    val defaultPerdidas = listOf("Saque", "Remate", "Recepción", "Error no forzado")

    val ganadasTemp = remember {
        mutableStateListOf<TextFieldValue>().apply {
            defaultGanadas.forEach { add(TextFieldValue(it)) }
            repeat(5 - size) { add(TextFieldValue("")) }
        }
    }

    val perdidasTemp = remember {
        mutableStateListOf<TextFieldValue>().apply {
            defaultPerdidas.forEach { add(TextFieldValue(it)) }
            repeat(5 - size) { add(TextFieldValue("")) }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Text(
            text = "🏐 Personalización de estadísticas",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            fontSize = 22.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Text("Puntos Ganados", style = MaterialTheme.typography.titleMedium, color = Color.White)
        ganadasTemp.forEachIndexed { i, campo ->
            TextField(
                value = campo,
                onValueChange = { ganadasTemp[i] = it },
                label = { Text("Ganado ${i + 1}") },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.DarkGray,
                    focusedContainerColor = Color.Gray,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.LightGray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Puntos Perdidos", style = MaterialTheme.typography.titleMedium, color = Color.White)
        perdidasTemp.forEachIndexed { i, campo ->
            TextField(
                value = campo,
                onValueChange = { perdidasTemp[i] = it },
                label = { Text("Perdido ${i + 1}") },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.DarkGray,
                    focusedContainerColor = Color.Gray,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.LightGray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                columnasGanadas.clear()
                columnasGanadas.addAll(ganadasTemp.map { it.text }.filter { it.isNotBlank() })

                columnasPerdidas.clear()
                columnasPerdidas.addAll(perdidasTemp.map { it.text }.filter { it.isNotBlank() })

                onFinalizar()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray, contentColor = Color.White)
        ) {
            Text("Finalizar")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = {
                defaultGanadas.forEachIndexed { i, value ->
                    if (i < ganadasTemp.size) ganadasTemp[i] = TextFieldValue(value)
                }
                defaultPerdidas.forEachIndexed { i, value ->
                    if (i < perdidasTemp.size) perdidasTemp[i] = TextFieldValue(value)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
        ) {
            Text("Restaurar por defecto")
        }
    }
}
