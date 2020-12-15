package com.example.corona19

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "http://openapi.data.go.kr"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(SimpleXmlConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface CoronaApiService {
    @GET("openapi/service/rest/Covid19/getCovid19InfStateJson?serviceKey")
    fun getProperties():
            Call<String>
}

object CoronaApi {
    val retrofitService : CoronaApiService by lazy {
        retrofit.create(CoronaApiService::class.java)
    }
}