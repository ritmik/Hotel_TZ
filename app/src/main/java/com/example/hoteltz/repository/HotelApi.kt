package com.example.hoteltz.repository

import com.example.hoteltz.data.HotelData
import com.example.hoteltz.data.HotelDataDetail
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author Mustafin Rufat (@mustafin)
 */
interface HotelApi {

    @GET("0777.json")
    suspend fun getHotelList():Response<ArrayList<HotelData>>

    @GET("{id}.json")
    suspend fun getHotelDataById(@Path("id") id: Int): Response<HotelDataDetail>

}