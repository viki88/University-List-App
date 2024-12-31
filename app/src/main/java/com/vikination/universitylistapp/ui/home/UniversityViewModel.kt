package com.vikination.universitylistapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vikination.universitylistapp.data.University
import com.vikination.universitylistapp.data.UniversityRepository
import com.vikination.universitylistapp.data.source.network.NetworkDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeScreenUiState(
    val listUniversities: List<University> = emptyList(),
    val isLoading: Boolean = false,
    val isConnectionAvailable: Boolean = false
)

@HiltViewModel
class UniversityViewModel @Inject constructor(
    private val repository: UniversityRepository
) :ViewModel(){

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    val _isLoading = MutableStateFlow(false)
    private var _listUniversities: Flow<List<University>>

    private val _isConnectionAvailable = MutableStateFlow(false)
    val isConnectionAvailable = _isConnectionAvailable.asStateFlow()

    private val _isOnSearch = MutableStateFlow(false)
    val isOnSearch = _isOnSearch.asStateFlow()

    private val _selectedUniversity = MutableStateFlow(University.emptyUniversity())
    val selectedUniversity = _selectedUniversity.asStateFlow()

    /**
     * start observe connectivity status and stream universities data from local database
     */
    init {
        observeConnectivity()
        _listUniversities = repository.getUniversitiesStream()
    }

    /**
     * Combine [UniversityRepository.getUniversitiesStream] and [_isLoading]
     * this properties for update [HomeScreenUiState]
     */
    val homeScreenUiState = repository.getUniversitiesStream()
        .combine(
            _isLoading
        ){
            list, isLoading ->
            HomeScreenUiState(list, isLoading)
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            HomeScreenUiState()
        )

    /**
     * Combine [UniversityRepository.getUniversitiesStream] and [_searchText]
     * this property for filter [List<University>] while type some keyword in search box
     */
    val listUniversitiesUiState = combine(
        _searchText,
        _listUniversities
    ){ searchText, listUniversities ->
            if (searchText.isBlank()) listUniversities
            else {
                listUniversities.filter { it.name.contains(searchText, ignoreCase = true) }
            }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    /**
     * Observe connectivity status and update [_isConnectionAvailable]
     */
    fun observeConnectivity(){
        viewModelScope.launch(Dispatchers.IO){
            repository.connectivityObserver().collect{
                    status ->
                _isConnectionAvailable.update {
                    when (status) {
                        NetworkDataSource.Status.Available -> true
                        NetworkDataSource.Status.Lost,
                        NetworkDataSource.Status.Unavailable -> false
                    }
                }
            }
        }
    }

    /**
     * refresh data from network and update [_isLoading]
     */
    fun refresh(){
        viewModelScope.launch {
            _isLoading.update { true }
            repository.getUniversitiesFromNetwork().stateIn(viewModelScope)
            _isLoading.update { false }
        }
    }

    /**
     * update [_searchText]
     */
    fun onSearchTextChange(text :String){
        _searchText.update { text }
    }

    /**
     * update [_isOnSearch]
     */
    fun onClickedActionButton(){
        _isOnSearch.update { !_isOnSearch.value }
    }

    /**
     * update [_selectedUniversity]
     */
    fun setSelectedUniversity(university: University){
        _selectedUniversity.update { university }
    }
}