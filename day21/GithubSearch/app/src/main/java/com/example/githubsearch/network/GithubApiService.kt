package com.example.githubsearch.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://api.github.com"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface GithubApiService {
    @GET("search/repositories")
    fun getProperties(
        @Query("q") keyword : String): Call<GithubProperty>
    @GET("repos/{owner}/{repos}")
    fun getRepos(
            @Path("owner") owner: String?,
            @Path("repos") repos: String?): Call<Repos>
    @GET("users/{user}")
    fun getUsers(
            @Path("user") user: String?): Call<User>
}

object GithubApi {
    val retrofitService : GithubApiService by lazy {
        retrofit.create(GithubApiService::class.java)
    }
}