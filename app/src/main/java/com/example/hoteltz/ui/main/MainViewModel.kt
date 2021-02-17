package com.example.hoteltz.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hoteltz.data.HotelData
import com.example.hoteltz.repository.ApiFactory
import com.example.hoteltz.repository.HotelRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel : ViewModel() {
    val adapterRV: AdapterRV = AdapterRV()

    val allHotelLiveData = MutableLiveData<ArrayList<HotelData>?>()

    init {
        Log.d("DataRepository", "Init MainViewModel")
    }

    private val repository: HotelRepository = HotelRepository(ApiFactory.hotelApi)

    fun fetchAllHotels() {
        viewModelScope.launch {
            try {
                val allHotels = repository.getHotelList()
                allHotelLiveData.value = allHotels.body()
            } catch (e: IOException) {
                Log.d("DataRepository", "Ошибка связи, попробуйте зайти в приложение позже")
                allHotelLiveData.value = null
            }
        }
    }

    //_______________________________________________________________
    private fun onRun() {
        viewModelScope.launch {
            var x = 0
            //while (x < 10 && isActive) { //если в корутине нет suspend ф-ий, то она не остановится на cancel(), ножно по флагу isActive в ручную останавливать корутину
            while (x < 10) {
                //TimeUnit.MILLISECONDS.sleep(1000) //это не suspend функция
                delay(1000) //suspend ф-ия сама остановит корутину
                Log.d("DataRepository", "coroutine, ${x++}, isActive = ${isActive}")
            }
//            Log.d("DataRepository","parent coroutine, start")
//
//            val data = async {getData()} //здесь же происходит запуск
//            val data2 = async(start = CoroutineStart.LAZY) {getData2()} //запуск будет происходить при вызове await()
//
//            delay(3000)
//
//            val result = "${data.await()}, " +
//                    "${ data2.await()}"
//            Log.d("DataRepository","parent coroutine, children returned: $result")
//
//            Log.d("DataRepository","parent coroutine, end")
        }
    }

    private suspend fun getData(): String {
        delay(2000)
        return "data"
    }

    private suspend fun getData2(): String {
        delay(2500)
        return "data2"
    }
//_______________________________________________________________

    fun itemClickRV(hotelData: HotelData) {
        viewModelScope.cancel()
        Log.d("DataRepository", "itemClickTab = " + hotelData.id.toString())
    }

    fun sortByLen() {
        if (allHotelLiveData.value != null) {
            val ar: ArrayList<HotelData> = allHotelLiveData.value!!
            val size = ar.size
            if (size < 2) return //0 или 1 элемент в массиве - сортировка не требуется
            var ind: Int = 0

            while (ind < size - 1) {
                bubleSort(ar, ind)
                ind++
            }

            allHotelLiveData.value = ar
        }
    }

    private fun bubleSort(ar: ArrayList<HotelData>, ind: Int) {
        val temp: HotelData
        var i = ind
        if (ar[ind].distance > ar[ind + 1].distance) {
            temp = ar[ind]
            ar[ind] = ar[ind + 1]
            ar[ind + 1] = temp
            i--
            if (ind > 0) bubleSort(ar, i)
        }
    }

    fun sortByCount() {
        if (allHotelLiveData.value != null) {
            val ar: ArrayList<HotelData> = allHotelLiveData.value!!
            val size = ar.size
            if (size < 2) return //0 или 1 элемент в массиве - сортировка не требуется
            var ind: Int = 0

            while (ind < size - 1) {
                bubleSortByCount(ar, ind)
                ind++
            }

            allHotelLiveData.value = ar
        }
    }

    private fun bubleSortByCount(ar: ArrayList<HotelData>, ind: Int) {
        val temp: HotelData
        var i = ind
        if (countSuit(ar[ind].suites_availability) < countSuit(ar[ind + 1].suites_availability)) {
            temp = ar[ind]
            ar[ind] = ar[ind + 1]
            ar[ind + 1] = temp
            i--
            if (ind > 0) bubleSortByCount(ar, i)
        }
    }

    private fun countSuit(s: String): Int {
        val splitArray = s.split(":")
        return splitArray.size
    }
}