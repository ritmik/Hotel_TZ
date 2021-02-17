package com.example.hoteltz.data

/**
 * @author Mustafin Rufat (@mustafin)
 */
data class HotelDataDetail(
    val id: Int,
    val name: String,
    val address: String,
    val stars: Float,
    val distance: Float,
    val image: String,
    val suites_availability: String,
    val lat: Double,
    val lon: Double
)
