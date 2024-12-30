package com.vikination.universitylistapp.data.source.network

import kotlinx.coroutines.flow.Flow


interface NetworkDataSource {
    suspend fun loadAllUniversities(): List<NetworkUniversity>

    fun observeInternetConnection() : Flow<Status>

    enum class Status{
        Available,
        Unavailable,
        Lost
    }
}