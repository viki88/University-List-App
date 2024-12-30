package com.vikination.universitylistapp.data

import com.vikination.universitylistapp.data.source.network.NetworkDataSource
import kotlinx.coroutines.flow.Flow

interface UniversityRepository {

    fun getUniversitiesStream(): Flow<List<University>>

    suspend fun getUniversitiesFromNetwork()

    fun connectivityObserver(): Flow<NetworkDataSource.Status>
}