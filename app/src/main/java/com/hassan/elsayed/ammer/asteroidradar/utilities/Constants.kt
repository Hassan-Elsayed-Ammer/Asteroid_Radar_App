package com.hassan.elsayed.ammer.asteroidradar.utilities

import java.text.SimpleDateFormat
import java.util.*

object Constants {
    const val API_QUERY_DATE_FORMAT = "YYYY-MM-dd"
    const val DEFAULT_END_DATE_DAYS = 7
    const val BASE_URL = "https://api.nasa.gov/"
    const val API_KEY = "FkDGgZemPpazajLMDydrrRXONTCzqwE9dbgIyGNQ"
    const val DATA_BASE_NAME = "Asteroid Data Base"
    private const val PATTERN_DATE = "yyyy-MM-dd"

    fun formattedCurrentDate(): String {
        val formatter = SimpleDateFormat(PATTERN_DATE)
        val date = Date()
        return formatter.format(date)
    }
}