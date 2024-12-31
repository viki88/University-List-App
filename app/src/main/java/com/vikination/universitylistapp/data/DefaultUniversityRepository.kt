package com.vikination.universitylistapp.data

import com.vikination.universitylistapp.data.UniversityRepository.Resource
import com.vikination.universitylistapp.data.source.local.UniversityDao
import com.vikination.universitylistapp.data.source.network.NetworkDataSource
import com.vikination.universitylistapp.data.source.network.NetworkUniversity
import com.vikination.universitylistapp.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultUniversityRepository @Inject constructor(
    private val localDataSource: UniversityDao,
    private val networkDataSource: NetworkDataSource,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) :UniversityRepository {

    override fun getUniversitiesStream(): Flow<List<University>> {
        return localDataSource.observeAllUniversities().map {
            universities ->
            withContext(dispatcher){
                universities.toUniversityList()
            }
        }
    }

    override fun getUniversitiesFromNetwork(): Flow<Resource<List<NetworkUniversity>>> {
        return flow {
            val response = safeApiCall { networkDataSource.loadAllUniversities() }
            withContext(dispatcher){
                if (response is Resource.Success){
                    val universityListFromNetwork = response.data ?: emptyList()
                    localDataSource.deleteAll()
                    localDataSource.upsertAll(universityListFromNetwork.toLocalUniversityList())
                }
            }
            emit(response)
        }

    }

    /**
     * this function for observe connection internet status
     * @return Status
     */
    override fun connectivityObserver(): Flow<NetworkDataSource.Status> {
        return networkDataSource.observeInternetConnection()
    }

    override suspend fun getUniversityById(id: String): List<University> {
        return localDataSource.getUniversityById(id).toUniversityList()
    }


}