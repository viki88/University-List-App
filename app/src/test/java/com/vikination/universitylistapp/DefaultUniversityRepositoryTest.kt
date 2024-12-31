package com.vikination.universitylistapp

import com.vikination.universitylistapp.data.DefaultUniversityRepository
import com.vikination.universitylistapp.data.University
import com.vikination.universitylistapp.data.UniversityRepository
import com.vikination.universitylistapp.data.source.local.LocalUniversity
import com.vikination.universitylistapp.data.source.local.UniversityDao
import com.vikination.universitylistapp.data.source.network.NetworkDataSource
import com.vikination.universitylistapp.data.source.network.NetworkUniversity
import com.vikination.universitylistapp.data.toLocalUniversityList
import com.vikination.universitylistapp.data.toUniversityList
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class DefaultUniversityRepositoryTest {

    private val localDataSource :UniversityDao = mockk()
    private val networkDataSource = mockk<NetworkDataSource>()
    private val dispatcher = StandardTestDispatcher()
    private val repository = DefaultUniversityRepository(localDataSource, networkDataSource, dispatcher)

    @Before
    fun setUp(){
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun getUniversitiesStreamShouldReturnUniversitiesList() = runTest {

        mockkStatic(LocalUniversity::class)

        val mockLocalUniversities = listOf(
            LocalUniversity(1, "University 1", "Province 1", "www.example.com"),
            LocalUniversity(2, "University 2", "Province 2", "www.example2.com")
        )

        val mockUniversityList = listOf(
            University("University 1", "Province 1", "www.example.com"),
            University("University 2", "Province 2", "www.example2.com")
        )

        val mockFlow = flowOf(mockLocalUniversities)

        every { localDataSource.observeAllUniversities() } returns mockFlow

        mockkStatic("com.vikination.universitylistapp.data.ModelMappingExtKt")
        every { mockLocalUniversities.toUniversityList() } returns mockUniversityList

        val result = repository.getUniversitiesStream().toList()

        assert(result[0] == mockUniversityList)
        assert("Province 1" == result[0][0].province)
        assert("Province 2" == result[0][1].province)

    }

    @Test
    fun getUniversitiesFromNetworkShouldCallUpsertAll() = runTest(dispatcher) {

        val mockNetworkUniversities = listOf(
            NetworkUniversity("University 1", "Province 1", listOf("www.example.com")),
            NetworkUniversity("University 2", "Province 2", listOf("www.example2.com"))
        )

        val mockRepository = mockk<UniversityRepository>()

        coEvery { networkDataSource.loadAllUniversities() } returns Response.success(mockNetworkUniversities)
        coJustRun { localDataSource.deleteAll() }
        coJustRun { localDataSource.upsertAll(mockNetworkUniversities.toLocalUniversityList()) }
        coEvery { mockRepository.getUniversitiesFromNetwork() } returns flowOf(
            UniversityRepository.Resource.Success(mockNetworkUniversities)
        )

        val result = mockRepository.getUniversitiesFromNetwork().toList()

        assert(result.size == 1)
        val emittedResource = result.first()
        assert(emittedResource is UniversityRepository.Resource.Success)
        assert((emittedResource as UniversityRepository.Resource.Success).data == mockNetworkUniversities)

    }

    @Test
    fun connectivityObserverShouldReturnStatus() = runTest {
        val mockFlowStatus = flowOf(
            NetworkDataSource.Status.Available,
            NetworkDataSource.Status.Unavailable,
            NetworkDataSource.Status.Lost
        )

        every { networkDataSource.observeInternetConnection() } returns  mockFlowStatus

        val emitedStatues = repository.connectivityObserver().toList()

        assert(
            emitedStatues == listOf(
                NetworkDataSource.Status.Available,
                NetworkDataSource.Status.Unavailable,
                NetworkDataSource.Status.Lost
            )
        )

        verify { networkDataSource.observeInternetConnection() }

    }

    @Test
    fun getUniversityByIdShouldReturnUniversity() = runTest {
        val id = "1"
        val mockLocalUniversities = listOf(
            LocalUniversity(1, "University 1", "Province 1", "www.example.com")
        )
        val mockUniversityList = listOf(
            University("University 1", "Province 1", "www.example.com")
        )

        mockkStatic("com.vikination.universitylistapp.data.ModelMappingExtKt")
        coEvery { localDataSource.getUniversityById(id) } returns mockLocalUniversities
        every { mockLocalUniversities.toUniversityList() } returns mockUniversityList

        val result = repository.getUniversityById(id)

        assert(result == mockUniversityList)

        coVerify { localDataSource.getUniversityById(id) }
        verify { mockLocalUniversities.toUniversityList() }
    }

}