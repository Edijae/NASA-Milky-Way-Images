package com.samuel.domain.repositories

import com.samuel.domain.models.Country


interface CountriesRepository {
    fun getCountries(): List<Country>
}