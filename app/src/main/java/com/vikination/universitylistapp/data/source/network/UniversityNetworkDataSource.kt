package com.vikination.universitylistapp.data.source.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class UniversityNetworkDataSource @Inject constructor (
    private var apiService: ApiService,
    @ApplicationContext var context: Context
) :NetworkDataSource{

    /**
     * fetch universities data from API
     * @return all universities
     */
    override suspend fun loadAllUniversities(): Response<List<NetworkUniversity>> {
        return apiService.getUniversities()
    }

    /**
     * Observe internet connection and return status
     * @return NetworkDataSource.Status
     */
    override fun observeInternetConnection(): Flow<NetworkDataSource.Status> {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return callbackFlow {
            val callback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    launch { send( NetworkDataSource.Status.Available ) }
                }

                override fun onLost(network: Network) {
                    launch { send( NetworkDataSource.Status.Lost ) }
                }

                override fun onUnavailable() {
                    launch { send( NetworkDataSource.Status.Unavailable ) }
                }
            }

            connectivityManager.registerDefaultNetworkCallback(callback)

            awaitClose{
                connectivityManager.unregisterNetworkCallback(callback)
            }
        }.distinctUntilChanged()
    }

}