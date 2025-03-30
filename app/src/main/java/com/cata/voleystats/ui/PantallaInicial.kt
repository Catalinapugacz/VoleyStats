package com.cata.voleystats.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cata.voleystats.ui.componentes.BotonOpcion

@Composable
fun PantallaInicial(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "VoleyStats",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(32.dp))

        BotonOpcion("Iniciar nuevo partido") {
            navController.navigate("seleccionEquipos")
        }

        BotonOpcion("Ver historial") {
            navController.navigate("historial")
        }

        BotonOpcion("Crear nuevo equipo") {
            navController.navigate("gestionEquipos")
        }

        BotonOpcion("Consultar estadísticas") {
            // Futuro desarrollo
        }
    }
}
