package com.cata.voleystats

import android.os.Bundle
import android.content.pm.ActivityInfo
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cata.voleystats.data.AppDatabase
import com.cata.voleystats.ui.*
import com.cata.voleystats.ui.theme.VoleyStatsTheme
import com.cata.voleystats.ui.viewmodel.SeleccionEquiposViewModel
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VoleyStatsTheme {
                val navController = rememberNavController()
                val activity = LocalContext.current as ComponentActivity


                val columnasGanadas = remember { mutableStateListOf("Saque", "Remate", "Espacio Vacío") }
                val columnasPerdidas = remember { mutableStateListOf("Choque", "Flecha", "Doble Golpe") }
                val equipoLocal = remember { mutableStateOf("") }
                val equipoVisitante = remember { mutableStateOf("") }

                val jugadoresEquipoLocal = rememberSaveable(
                    saver = listSaver(
                        save = { it.toList() },
                        restore = { it.toMutableStateList() as SnapshotStateList<String> }
                    )
                ) {
                    mutableStateListOf<String>()
                }

                NavHost(navController = navController, startDestination = "pantallaInicial") {

                    composable("pantallaInicial") {
                        PantallaInicial(navController)
                    }

                    composable("seleccionEquipos") {
                        PantallaSeleccionEquipos(
                            equipoLocal = equipoLocal,
                            equipoVisitante = equipoVisitante,
                            onElegirFavorito = {},
                            navController = navController
                        )
                    }

                    composable("cargaJugadores") {
                        val context = LocalContext.current
                        val db = AppDatabase.getInstance(context)
                        val viewModel: SeleccionEquiposViewModel = viewModel(
                            factory = SeleccionEquiposViewModel.Factory(db.equipoFavoritoDao())
                        )

                        PantallaCargaJugadores(
                            equipoLocal = equipoLocal.value,
                            onGuardarEquipo = { nombreEquipo, jugadores, guardar ->
                                jugadoresEquipoLocal.clear()
                                jugadoresEquipoLocal.addAll(jugadores)

                                if (guardar) {
                                    viewModel.guardarSiEsFavorito(
                                        nombre = nombreEquipo,
                                        jugadores = jugadores,
                                        guardar = true
                                    )
                                }
                            },
                            onContinuar = { navController.navigate("personalizacion") },
                            onVolver = { navController.popBackStack() }
                        )
                    }

                    composable("personalizacion") {
                        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

                        PantallaPersonalizacion(
                            columnasGanadas,
                            columnasPerdidas,
                            equipoLocal,
                            equipoVisitante,
                            onFinalizar = { navController.navigate("estadisticas") }
                        )
                    }

                    composable("estadisticas") {
                        val context = LocalContext.current
                        val activity = context as ComponentActivity

                        LaunchedEffect(Unit) {
                            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                        }

                        PantallaEstadisticas(
                            columnasGanadas = columnasGanadas,
                            columnasPerdidas = columnasPerdidas,
                            equipoLocal = equipoLocal.value,
                            equipoVisitante = equipoVisitante.value,
                            jugadoresNombres = jugadoresEquipoLocal,
                            onVolver = {
                                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                                navController.popBackStack()
                            },
                            onPartidoFinalizado = {
                                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                                navController.navigate("historial")
                            }
                        )
                    }

                    composable("historial") {
                        PantallaHistorial(
                            onVolver = { navController.navigate("personalizacion") }
                        )
                    }

                    composable("gestionEquipos") {
                        Text("Pantalla Gestión de Equipos (próximamente)")
                    }
                }
            }
        }
    }
}
