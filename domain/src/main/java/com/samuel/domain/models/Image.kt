package com.samuel.domain.models

import java.io.Serializable

data class Image(
   val id: Int,
   val album: List<String> = arrayListOf(),
   val center: String? = null,
   val title: String? = null,
   val keywords: List<String> = arrayListOf(),
   val location: String? = null,
   val nasaId: String? = null,
   val dateCreated: String? = null,
   val description: String? = null,
   var href: String? = null,
) : Serializable
