package com.example.hoteltz.data

/**
 * @author Mustafin Rufat (@mustafin)
 */
data class HotelData(
    val id: Int,
    val name: String,
    val address: String,
    val stars: Float,
    val distance: Float,
    val suites_availability: String
)
