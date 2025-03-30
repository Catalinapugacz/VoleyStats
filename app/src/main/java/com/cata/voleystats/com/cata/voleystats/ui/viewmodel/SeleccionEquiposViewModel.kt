package com.cata.voleystats.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.cata.voleystats.data.EquipoFavoritoDao
import kotlinx.coroutines.launch
import com.cata.voleystats.data.EquipoFavoritoEntity


class SeleccionEquiposViewModel(
    private val equipoFavoritoDao: EquipoFavoritoDao
) : ViewModel() {

    fun guardarSiEsFavorito(nombre: String, jugadores: List<String>, guardar: Boolean) {
        if (!guardar) return
        viewModelScope.launch {
            val equipo = EquipoFavoritoEntity(nombre = nombre, jugadores = jugadores)
            equipoFavoritoDao.insertarEquipo(equipo)
        }
    }

    class Factory(private val dao: EquipoFavoritoDao) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SeleccionEquiposViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SeleccionEquiposViewModel(dao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
