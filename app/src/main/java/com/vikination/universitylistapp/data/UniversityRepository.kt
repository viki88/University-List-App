package com.vikination.universitylistapp.data

import com.vikination.universitylistapp.data.source.network.NetworkDataSource
import com.vikination.universitylistapp.data.source.network.NetworkUniversity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response

interface UniversityRepository {

    fun getUniversitiesStream(): Flow<List<University>>

    fun getUniversitiesFromNetwork(): Flow<Resource<List<NetworkUniversity>>>

    fun connectivityObserver(): Flow<NetworkDataSource.Status>

    suspend fun getUniversityById(id :String): List<University>

    suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>): Resource<T> {

        // Returning api response
        // wrapped in Resource class
        return withContext(Dispatchers.IO) {
            try {

                // Here we are calling api lambda
                // function that will return response
                // wrapped in Retrofit's Response class
                val response: Response<T> = apiToBeCalled()

                if (response.isSuccessful) {
                    // In case of success response we
                    // are returning Resource.Success object
                    // by passing our data in it.
                    Resource.Success(data = response.body()!!)
                } else {
                    // parsing api's own custom json error
                    // response in ExampleErrorResponse pojo
                    // Simply returning api's own failure message
                    Resource.Error(errorMessage = "Something went wrong")
                }

            } catch (e: HttpException) {
                // Returning HttpException's message
                // wrapped in Resource.Error
                Resource.Error(errorMessage = e.message ?: "Something went wrong")
            } catch (e: IOException) {
                // Returning no internet message
                // wrapped in Resource.Error
                Resource.Error("Please check your network connection")
            } catch (e: Exception) {
                // Returning 'Something went wrong' in case
                // of unknown error wrapped in Resource.Error
                Resource.Error(errorMessage = "Something went wrong")
            }
        }
    }

    sealed class Resource<T>(
        val data: T? = null,
        val message: String? = null
    ) {

        class Success<T>(data: T) : Resource<T>(data = data)

        class Error<T>(errorMessage: String) : Resource<T>(message = errorMessage)

        class Loading<T> : Resource<T>()
    }
}