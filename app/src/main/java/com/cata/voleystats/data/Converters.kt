package com.cata.voleystats.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromStringList(value: List<String>): String = gson.toJson(value)

    @TypeConverter
    fun toStringList(value: String): List<String> =
        gson.fromJson(value, object : TypeToken<List<String>>() {}.type)

    @TypeConverter
    fun fromMap(value: Map<String, Int>): String = gson.toJson(value)

    @TypeConverter
    fun toMap(value: String): Map<String, Int> =
        gson.fromJson(value, object : TypeToken<Map<String, Int>>() {}.type)
}