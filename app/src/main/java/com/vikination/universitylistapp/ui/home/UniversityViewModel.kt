package com.vikination.universitylistapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vikination.universitylistapp.data.University
import com.vikination.universitylistapp.data.UniversityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UniversityUiState(
    val universities: List<University> = emptyList(),
    val isLoading: Boolean = false,
    val message: String = ""

)

@HiltViewModel
class UniversityViewModel @Inject constructor(
    private val repository: UniversityRepository
) :ViewModel(){

    val _isLoading = MutableStateFlow(false)
    val _listUniversities = repository.getUniversitiesStream()
    val _message = MutableStateFlow("")

    val uiState :StateFlow<UniversityUiState> = combine(
        _listUniversities, _isLoading, _message
    ){ listUniversities, isLoading, message ->
        UniversityUiState(listUniversities, isLoading, message)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500),
        initialValue = UniversityUiState(isLoading = true)
    )

    fun refresh(){
        viewModelScope.launch {
            repository.getUniversitiesFromNetwork()
        }
    }
}