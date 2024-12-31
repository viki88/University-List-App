package com.vikination.universitylistapp

import android.content.Context
import android.net.ConnectivityManager
import com.vikination.universitylistapp.data.DefaultUniversityRepository
import com.vikination.universitylistapp.data.source.local.UniversityDao
import com.vikination.universitylistapp.data.source.network.ApiService
import com.vikination.universitylistapp.data.source.network.NetworkDataSource
import com.vikination.universitylistapp.data.source.network.NetworkUniversity
import com.vikination.universitylistapp.data.source.network.UniversityNetworkDataSource
import io.mockk.CapturingSlot
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UniversityNetworkDataSourceTest {

    private val apiService = mockk<ApiService>(relaxed = true)
    private val context = mockk<Context>(relaxed = true)
    private val connectivityManager: ConnectivityManager = mockk(relaxed = true)
    private lateinit var callbackSlot: CapturingSlot<ConnectivityManager.NetworkCallback>
    private val universityNetworkDataSource :NetworkDataSource = UniversityNetworkDataSource(apiService, context)
    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp(){
        every { context.getSystemService(Context.CONNECTIVITY_SERVICE) } returns connectivityManager
        callbackSlot = slot()
    }

    @Test
    fun loadAllUniversitiesShouldCallApiServiceGetUniversities() = runTest {
        val mockNetworkUniversities = listOf(
            NetworkUniversity("University 1", "Province 1", listOf("www.example.com")),
            NetworkUniversity("University 2", "Province 2", listOf("www.example2.com"))
        )

        coEvery { apiService.getUniversities() } returns mockNetworkUniversities

        val result = universityNetworkDataSource.loadAllUniversities()

        assert(result == mockNetworkUniversities)

        coVerify(exactly = 1) { apiService.getUniversities() }
    }

    @Test
    fun observeInternetConnectionEmitsStatusesCorrectly() = runTest(dispatcher) {

        every { connectivityManager.registerDefaultNetworkCallback(capture(callbackSlot)) } just Runs
        every { connectivityManager.unregisterNetworkCallback(any() as ConnectivityManager.NetworkCallback) } just Runs

        val emittedStatus = mutableListOf<NetworkDataSource.Status>()
        val job = launch {
            universityNetworkDataSource.observeInternetConnection().collect { status ->
                println("Status emitted: $status")
                emittedStatus.add(status)
            }
        }

        callbackSlot.captured.onAvailable(mockk())
        callbackSlot.captured.onLost(mockk())
        callbackSlot.captured.onUnavailable()

        advanceUntilIdle()

        println("emittedStatus: $emittedStatus")

        assert(emittedStatus == listOf(
            NetworkDataSource.Status.Available,
            NetworkDataSource.Status.Lost,
            NetworkDataSource.Status.Unavailable
        ))

        job.cancel()

        verify { connectivityManager.unregisterNetworkCallback(any() as ConnectivityManager.NetworkCallback) }
    }
}