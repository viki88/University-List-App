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
import javax.inject.Inject

class UniversityNetworkDataSource @Inject constructor (
    private var apiService: ApiService,
    @ApplicationContext var context: Context
) :NetworkDataSource{

    /**
     * fetch universities data from API
     * @return all universities
     */
    override suspend fun loadAllUniversities(): List<NetworkUniversity> {
        return apiService.getUniversities()
    }

    override fun observeInternetConnection(): Flow<NetworkDataSource.Status> {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return callbackFlow {
            val callback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    println("connection univ available")
                    launch { send( NetworkDataSource.Status.Available ) }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    println("connection univ connection lost")
                    launch { send( NetworkDataSource.Status.Lost ) }
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    println("connection univ unavailable")
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