package com.vikination.universitylistapp.data.source.network


import com.vikination.universitylistapp.data.University
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search")
    suspend fun getUniversities(
        @Query("country") country: String = "indonesia"
    ): List<University>

}