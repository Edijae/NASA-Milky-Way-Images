package com.samuel.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.samuel.data.utils.Converter

@Entity
@TypeConverters(Converter::class)
data class ImageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var href: String? = null,
    var data: ArrayList<DataEntity> = arrayListOf(),
    var links: ArrayList<LinkEntity> = arrayListOf()

)
