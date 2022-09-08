package com.samuel.data.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.samuel.data.models.DataEntity
import com.samuel.data.models.LinkEntity

internal class Converter {
    val gson = Gson()
    val dataType = object : TypeToken<List<DataEntity>>() {}.type
    val linkType = object : TypeToken<List<LinkEntity>>() {}.type

    @TypeConverter
    fun dataToString(value: List<DataEntity>): String {
        return gson.toJson(value, dataType)
    }

    @TypeConverter
    fun stringToData(value: String): ArrayList<DataEntity> {
        return gson.fromJson(value, dataType)
    }

    @TypeConverter
    fun linkToString(value: List<LinkEntity>): String {
        return gson.toJson(value, linkType)
    }

    @TypeConverter
    fun stringToLinkDb(value: String): ArrayList<LinkEntity> {
        return gson.fromJson(value, linkType)
    }
}