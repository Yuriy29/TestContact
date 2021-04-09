package com.example.testcontactlistapp.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RoomDataConverter {

    private val gson = Gson()

    @TypeConverter
    fun valueToString(values: List<String>?): String? = values?.let { gson.toJson(it) }

    @TypeConverter
    fun valuesListToStruct(value: String?): List<String>? = value?.let {
        val listType = object : TypeToken<List<String>>() {}.type
        gson.fromJson(it, listType)
    }
}