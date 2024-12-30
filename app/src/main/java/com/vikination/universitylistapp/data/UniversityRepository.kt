package com.vikination.universitylistapp.data

import kotlinx.coroutines.flow.Flow

interface UniversityRepository {

    fun getUniversitiesStream(): Flow<List<University>>

    suspend fun getUniversitiesFromNetwork()
}