package com.samuel.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
class Metadata(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    @SerializedName("total_hits") val totalHits: Int = 0
)
