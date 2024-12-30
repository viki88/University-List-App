package com.vikination.universitylistapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vikination.universitylistapp.data.University
import com.vikination.universitylistapp.data.UniversityRepository
import com.vikination.universitylistapp.data.source.network.NetworkDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UniversityUiState(
    val universities: List<University> = emptyList(),
    val isLoading: Boolean = false,
    val message: String = "",
    val isConnectionAvailable: Boolean = true

)

@HiltViewModel
class UniversityViewModel @Inject constructor(
    private val repository: UniversityRepository
) :ViewModel(){

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    private val _listUniversities = repository.getUniversitiesStream()
    private val _message = MutableStateFlow("")
    private val _isConnectionAvailable = MutableStateFlow(false)
    private val _isOnSearch = MutableStateFlow(false)
    val isOnSearch = _isOnSearch.asStateFlow()

    init {
        observeConnectivity()
    }

    private fun observeConnectivity(){
        viewModelScope.launch(Dispatchers.IO){
            repository.connectivityObserver().collect{
                status ->
                _isConnectionAvailable.value = when(status){
                    NetworkDataSource.Status.Available -> true
                    NetworkDataSource.Status.Lost,
                    NetworkDataSource.Status.Unavailable -> false
                }
            }
        }
    }

    val uiState :StateFlow<UniversityUiState> = combine(
        _searchText,
        _listUniversities,
        _isLoading,
        _message,
        _isConnectionAvailable
    ){ searchText, listUniversities, isLoading, message , isConnectionAvailable->
        UniversityUiState(
            if (searchText.isBlank()) listUniversities
            else {
                listUniversities.filter { it.name.contains(searchText, ignoreCase = true) }
            }, isLoading, message, isConnectionAvailable)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500),
        initialValue = UniversityUiState(isLoading = true)
    )

    fun refresh(){
        _isLoading.update { true }
        viewModelScope.launch {
            if (_isConnectionAvailable.value){
                repository.getUniversitiesFromNetwork()
            }
            _isLoading.update { false }
        }
    }

    fun onSearchTextChange(text :String){
        _searchText.update { text }
    }

    fun onClickedActionButton(){
        _isOnSearch.update { !_isOnSearch.value }
    }

}