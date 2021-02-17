package com.example.hoteltz.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hoteltz.data.HotelData
import com.example.hoteltz.data.HotelDataDetail
import com.example.hoteltz.repository.ApiFactory
import com.example.hoteltz.repository.HotelRepository
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * @author Mustafin Rufat (@mustafin)
 */
class ItemViewModel : ViewModel() {
    init {
        Log.d("DataRepository", "Init ItemViewModel")
    }

    val datailHotelById = MutableLiveData<HotelDataDetail?>()

    private val repository = HotelRepository(ApiFactory.hotelApi)

    fun fetchHotelById(id: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getHotelDataById(id)
                datailHotelById.value = response.body()
            } catch (e: IOException) {
                Log.d("DataRepository", "Ошибка связи, попробуйте зайти в приложение позже")
                datailHotelById.value = null
            }
        }
    }

    fun itemClickRV(hotelData: HotelData) {
        Log.d("DataRepository", "itemClickTab = " + hotelData.id.toString())
    }
}