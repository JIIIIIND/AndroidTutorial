package com.example.corona19

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class MainViewModel : ViewModel() {
    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    private val _date = MutableLiveData<String>()
    val date: LiveData<String>
        get() = _date

    private val _cnt = MutableLiveData<String>()
    val cnt: LiveData<String>
        get() = _cnt

    init {
        getCoronaProperties()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCoronaProperties() {
        val date = LocalDate.now()
        val pattern = DateTimeFormatter.ofPattern("yyyyMMdd")
        val curDay = date.format(pattern)
        val preDay = date.minusDays(1).format(pattern)
        var covidCnt = 0
        CoronaApi.retrofitService.getProperties(
//                "EzoCrM4K%2BvEBJjeY9FGY7Mi7uqk%2FMHP3NOmqDgRJzPAWMxQ3jzznKIXQmIN6bbGEQXCPoWNqnQ6ZiQ7RFwCbSw%3D%3D",
        "1", "10", preDay, curDay).enqueue(
            object: Callback<com.example.corona19.Response> {
                override fun onResponse(call: Call<com.example.corona19.Response>, response: Response<com.example.corona19.Response>) {
                    covidCnt = response.body()?.body?.items?.item?.get(0)?.decideCnt?.toInt()!! -
                            response.body()?.body?.items?.item?.get(1)?.decideCnt?.toInt()!!
                    _date.value = curDay
                    _cnt.value = covidCnt.toString()
                }

                override fun onFailure(call: Call<com.example.corona19.Response>, t: Throwable) {
                    _response.value = "Failure: " + t.message
                }
            })
    }
}