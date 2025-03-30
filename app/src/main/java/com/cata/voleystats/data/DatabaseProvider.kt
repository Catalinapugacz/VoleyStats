package com.cata.voleystats.data

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    private var db: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        if (db == null) {
            db = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "voley_stats_db"


            )
                .fallbackToDestructiveMigration()
                .build()
        }
        return db!!
    }
}


