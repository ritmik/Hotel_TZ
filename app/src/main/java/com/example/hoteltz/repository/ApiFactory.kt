package com.example.hoteltz.repository

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * @author Mustafin Rufat (@mustafin)
 */
object ApiFactory {

//    private val okHttpClient = OkHttpClient().newBuilder()
//        .readTimeout(1000, TimeUnit.MILLISECONDS)
//        .connectTimeout(1000, TimeUnit.MILLISECONDS)
//        .writeTimeout(1000, TimeUnit.MILLISECONDS)
//        .build()


    private fun retrofit(): Retrofit = Retrofit.Builder()
        //.client(okHttpClient)
        .baseUrl("https://raw.githubusercontent.com/iMofas/ios-android-test/master/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val hotelApi: HotelApi = retrofit().create(HotelApi::class.java)

}