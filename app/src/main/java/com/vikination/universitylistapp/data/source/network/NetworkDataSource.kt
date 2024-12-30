package com.vikination.universitylistapp.data.source.network


interface NetworkDataSource {
    suspend fun loadAllUniversities(): List<NetworkUniversity>
}