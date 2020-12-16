package com.example.corona19

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "http://openapi.data.go.kr/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(SimpleXmlConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface CoronaApiService {
    @GET("openapi/service/rest/Covid19/getCovid19InfStateJson")
    fun getProperties(
            @Query("serviceKey") key : String,
            @Query("pageNo") pageNo : String,
            @Query("numOfRows") numOfRows : String,
            @Query("startCreateDt") start : String,
            @Query("endCreateDt") end : String
    ):
            Call<Response>
}

object CoronaApi {
    val retrofitService : CoronaApiService by lazy {
        retrofit.create(CoronaApiService::class.java)
    }
}