package com.samuel.domain.models

data class Country(
    val name: String,
    val phonePrefix: String,
    val flag: Int,
    val phoneNumberLength: Int,
    val currency: String
)
