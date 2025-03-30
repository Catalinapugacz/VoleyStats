package com.cata.voleystats

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.cata.voleystats.data.DatabaseProvider
import com.cata.voleystats.data.PartidoEntity
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun PantallaHistorial(
    onVolver: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var partidos by remember { mutableStateOf<List<PartidoEntity>>(emptyList()) }

    // Cargar partidos al iniciar pantalla
    LaunchedEffect(Unit) {
        val db = DatabaseProvider.getDatabase(context)
        partidos = db.partidoDao().getAll()
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text("📜 Historial de Partidos", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onVolver) {
                Text("🔙 Volver")
            }
            Button(onClick = {
                scope.launch {
                    val db = DatabaseProvider.getDatabase(context)
                    db.partidoDao().deleteAll()
                    partidos = emptyList()
                    Toast.makeText(context, "🗑️ Historial borrado", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text("🧹 Limpiar historial")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(partidos) { partido ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        val fecha = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                            .format(Date(partido.fecha))
                        Text("📅 $fecha", style = MaterialTheme.typography.labelSmall)

                        val resumen = "${partido.equipoVisitante} (${partido.setsVisitante}) - ${partido.equipoLocal} (${partido.setsLocal}) - ${partido.resultadoFinal}"
                        // Esto es temporal hasta que agreguemos resultadoFinal como campo
                        Text(resumen, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}
