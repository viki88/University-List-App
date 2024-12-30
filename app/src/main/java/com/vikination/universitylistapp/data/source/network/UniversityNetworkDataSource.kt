package com.vikination.universitylistapp.data.source.network

import javax.inject.Inject

class UniversityNetworkDataSource @Inject constructor (private var apiService: ApiService) :NetworkDataSource{

    /**
     * fetch universities data from API
     * @return all universities
     */
    override suspend fun loadAllUniversities(): List<NetworkUniversity> {
        return apiService.getUniversities()
    }

}