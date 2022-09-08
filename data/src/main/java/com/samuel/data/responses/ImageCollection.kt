package com.samuel.data.responses

import com.samuel.data.models.ImageEntity
import com.samuel.data.models.Metadata

class ImageCollection(
    val version: String,
    val href: String,
    val items: List<ImageEntity>,
    val metadata: Metadata
)
