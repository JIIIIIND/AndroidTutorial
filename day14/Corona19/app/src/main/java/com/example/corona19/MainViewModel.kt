package com.example.corona19

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    init {
        getCoronaProperties()
    }

    private fun getCoronaProperties() {
        CoronaApi.retrofitService.getProperties(
                "EzoCrM4K%2BvEBJjeY9FGY7Mi7uqk%2FMHP3NOmqDgRJzPAWMxQ3jzznKIXQmIN6bbGEQXCPoWNqnQ6ZiQ7RFwCbSw%3D%3D",
        "1", "10", "20200310", "20200315").enqueue(
            object: Callback<com.example.corona19.Response> {
                override fun onResponse(call: Call<com.example.corona19.Response>, response: Response<com.example.corona19.Response>) {
                    _response.value = response.body().toString()
                }

                override fun onFailure(call: Call<com.example.corona19.Response>, t: Throwable) {
                    _response.value = "Failure: " + t.message
                }
            })
    }
}