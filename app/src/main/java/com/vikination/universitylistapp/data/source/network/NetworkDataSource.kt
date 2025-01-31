package com.vikination.universitylistapp.data.source.network

import kotlinx.coroutines.flow.Flow
import retrofit2.Response


interface NetworkDataSource {
    suspend fun loadAllUniversities(): Response<List<NetworkUniversity>>

    fun observeInternetConnection() : Flow<Status>

    /**
     * Status for connectivity manager
     */
    enum class Status{
        Available,
        Unavailable,
        Lost
    }
}