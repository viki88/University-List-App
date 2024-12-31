package com.vikination.universitylistapp.data.source.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search")
    suspend fun getUniversities(
        @Query("country") country: String = "indonesia"
    ): Response<List<NetworkUniversity>>

}