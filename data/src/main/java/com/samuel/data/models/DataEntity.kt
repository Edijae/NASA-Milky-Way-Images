package com.samuel.data.models

import com.google.gson.annotations.SerializedName

data class DataEntity(
    val album: List<String>? = arrayListOf(),
    val center: String,
    val title: String,
    val keywords: List<String> = arrayListOf(),
    val location: String,
    @SerializedName("nasa_id") val nasaId: String,
    @SerializedName("date_created") val dateCreated: String,
    @SerializedName("media_type") val mediaType: String,
    val description: String? = null
)
