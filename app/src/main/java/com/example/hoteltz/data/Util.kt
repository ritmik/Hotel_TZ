package com.example.hoteltz.data

/**
 * @author Mustafin Rufat (@mustafin)
 */

fun convert(i: HotelData): HotelDataString {
    return HotelDataString(
        "id = " + i.id.toString(),
        "name = " + i.name,
        "address = " + i.address,
        "stars = " + i.stars.toString(),
        "distance = " + i.distance.toString(),
        "suites availability = " + i.suites_availability
    )
}

fun convertDetail(i: HotelDataDetail): HotelDataDetailString {
    return HotelDataDetailString(
        "id = " + i.id.toString(),
        "name = " + i.name,
        "address = " + i.address,
        "stars = " + i.stars.toString(),
        "distance = " + i.distance.toString(),
        "image = " + i.image,
        "suites availability = " + i.suites_availability,
        "lat = " + i.lat.toString(),
        "lon = " + i.lon.toString()
    )
}