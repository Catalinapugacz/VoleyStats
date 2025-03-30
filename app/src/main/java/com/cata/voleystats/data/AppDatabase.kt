package com.cata.voleystats.data

import android.content.Context
import androidx.room.*
import com.cata.voleystats.data.Converters
import com.cata.voleystats.data.EquipoFavoritoEntity
import com.cata.voleystats.data.EquipoPersonalizadoEntity
import com.cata.voleystats.data.JugadorEntity
import com.cata.voleystats.data.PartidoEntity

@Database(
    entities = [
        PartidoEntity::class,
        JugadorEntity::class,
        EquipoPersonalizadoEntity::class,
        EquipoFavoritoEntity::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun partidoDao(): PartidoDao
    abstract fun jugadorDao(): JugadorDao
    abstract fun equipoDao(): EquipoDao
    abstract fun equipoFavoritoDao(): EquipoFavoritoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "voley_stats_db"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
