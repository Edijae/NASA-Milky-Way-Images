package com.samuel.domain.models

data class ExchangeRate(
    var disclaimer: String? = null,
    var license: String? = null,
    var timestamp: Int? = null,
    var base: String? = null,
    var rates: Rates = Rates()
)
