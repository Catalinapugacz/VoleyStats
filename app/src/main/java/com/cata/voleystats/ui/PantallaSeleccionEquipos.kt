package com.cata.voleystats.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cata.voleystats.data.AppDatabase
import com.cata.voleystats.ui.componentes.BotonOpcion
import com.cata.voleystats.ui.viewmodel.SeleccionEquiposViewModel

@Composable
fun PantallaSeleccionEquipos(
    equipoLocal: MutableState<String>,
    equipoVisitante: MutableState<String>,
    onElegirFavorito: () -> Unit,
    navController: NavController
) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("voley_prefs", Context.MODE_PRIVATE)

    val db = AppDatabase.getInstance(context)
    val viewModel: SeleccionEquiposViewModel = viewModel(
        factory = SeleccionEquiposViewModel.Factory(db.equipoFavoritoDao())
    )

    var mostrarDialogoFavoritos by remember { mutableStateOf(false) }
    val equiposFavoritos = listOf("Team Cocodrilo", "Team Fénix", "Team Jaguar") // Simulado

    LaunchedEffect(Unit) {
        val ultimoEquipo = prefs.getString("ultimo_equipo_local", "") ?: ""
        equipoLocal.value = ultimoEquipo
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Configurar Equipos", color = Color.White, fontSize = 24.sp)

        Spacer(modifier = Modifier.height(24.dp))

        // Equipo Local
        Text("Equipo Local", color = Color.White, fontSize = 16.sp)
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = equipoLocal.value,
                onValueChange = {
                    equipoLocal.value = it
                    prefs.edit().putString("ultimo_equipo_local", it).apply()
                },
                label = { Text("Nombre equipo local") },
                colors = darkTextFieldColors(),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { mostrarDialogoFavoritos = true }) {
                Text("Favoritos")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Equipo Visitante
        Text("Equipo Visitante", color = Color.White, fontSize = 16.sp)
        TextField(
            value = equipoVisitante.value,
            onValueChange = { equipoVisitante.value = it },
            label = { Text("Nombre equipo visitante") },
            colors = darkTextFieldColors(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        BotonOpcion("Continuar") {
            if (equipoLocal.value.isNotBlank() && equipoVisitante.value.isNotBlank()) {
                navController.navigate("cargaJugadores") // Ir a la nueva pantalla
            } else {
                Toast.makeText(context, "Por favor, completá ambos nombres de equipo", Toast.LENGTH_SHORT).show()
            }
        }
    }

    if (mostrarDialogoFavoritos) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoFavoritos = false },
            title = { Text("Equipos Favoritos") },
            text = {
                Column {
                    equiposFavoritos.forEach { favorito ->
                        Text(
                            text = favorito,
                            color = Color.White,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable {
                                    equipoLocal.value = favorito
                                    prefs.edit().putString("ultimo_equipo_local", favorito).apply()
                                    mostrarDialogoFavoritos = false
                                }
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { mostrarDialogoFavoritos = false }) {
                    Text("Cerrar")
                }
            },
            containerColor = Color.DarkGray
        )
    }
}
