package com.vikination.universitylistapp

import com.vikination.universitylistapp.data.University
import com.vikination.universitylistapp.data.UniversityRepository
import com.vikination.universitylistapp.data.source.network.NetworkDataSource
import com.vikination.universitylistapp.ui.home.UniversityViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UniversityViewModelTest {

    private lateinit var viewModel: UniversityViewModel
    private val repository = mockk<UniversityRepository>()
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup(){
        Dispatchers.setMain(dispatcher)
        val mockUniversities = listOf(
            University("University 1", "Province 1", "www.university1.com")
        )
        every { repository.getUniversitiesStream() } returns flowOf(mockUniversities)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun observeConnectivityUpdatesIsConnectionAvailableToTrue() = runTest{

        val connectivityFlow = flowOf(NetworkDataSource.Status.Available)
        every { repository.connectivityObserver() } returns connectivityFlow

        viewModel = UniversityViewModel(repository)

        viewModel.observeConnectivity()

        assert(viewModel.isConnectionAvailable.value)

    }

    @Test
    fun observeConnectivityUpdatesIsConnectionAvailableToFalse() = runTest{

        val connectivityFlow = flowOf(NetworkDataSource.Status.Lost)
        every { repository.connectivityObserver() } returns connectivityFlow

        viewModel = UniversityViewModel(repository)

        viewModel.observeConnectivity()

        assert(!viewModel.isConnectionAvailable.value)

    }

    @Test
    fun refreshSetsLoadingStateAndFetchDataFromNetwork() = runTest{

        val connectivityFlow = flowOf(NetworkDataSource.Status.Available)
        every { repository.connectivityObserver() } returns connectivityFlow

        every { repository.getUniversitiesFromNetwork() } returns flowOf(UniversityRepository.Resource.Success(emptyList()))

        viewModel = UniversityViewModel(repository)

        viewModel.refresh()

        assert(!viewModel._isLoading.value)
    }

    @Test
    fun onSearchTextChangeUpdatesSearchText() = runTest{

        val connectivityFlow = flowOf(NetworkDataSource.Status.Available)
        every { repository.connectivityObserver() } returns connectivityFlow

        viewModel = UniversityViewModel(repository)

        val newSearchText = "New Search Text"
        viewModel.onSearchTextChange(newSearchText)

        assert(viewModel.searchText.value == newSearchText)
    }

    @Test
    fun setSelectedUniversityUpdatesSelectedUniversity() = runTest{
        val connectivityFlow = flowOf(NetworkDataSource.Status.Available)
        every { repository.connectivityObserver() } returns connectivityFlow

        viewModel = UniversityViewModel(repository)

        val newUniversity = University("University 2", "Province 2", "www.university2.com")
        viewModel.setSelectedUniversity(newUniversity)

        assert(viewModel.selectedUniversity.value == newUniversity)
    }

}