package com.example.hoteltz.repository

import com.example.hoteltz.data.HotelData
import com.example.hoteltz.data.HotelDataDetail
import retrofit2.Response
import retrofit2.http.Path

/**
 * @author Mustafin Rufat (@mustafin)
 */
class HotelRepository(private val api: HotelApi)  {

    suspend fun getHotelList() : Response<ArrayList<HotelData>> {
        return api.getHotelList()
    }

    suspend fun getHotelDataById( id: Int): Response<HotelDataDetail>{
        return api.getHotelDataById(id)
    }
}