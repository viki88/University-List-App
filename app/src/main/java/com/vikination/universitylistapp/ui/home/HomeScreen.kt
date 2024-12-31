package com.vikination.universitylistapp.ui.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.vikination.universitylistapp.DETAIL
import com.vikination.universitylistapp.ui.utils.UniversityAppBar
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: UniversityViewModel,
    modifier: Modifier = Modifier,
){
    val listUniversities by viewModel.listUniversitiesUiState.collectAsStateWithLifecycle()
    val isConnectionAvailable by viewModel.isConnectionAvailable.collectAsStateWithLifecycle()
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val isOnSearch by viewModel.isOnSearch.collectAsStateWithLifecycle()
    val homeScreenUiState by viewModel.homeScreenUiState.collectAsStateWithLifecycle()

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        modifier = modifier.fillMaxWidth(),
        topBar = {
            UniversityAppBar(
                viewModel::onClickedActionButton,
                isOnSearch
            )
        }
    ){ paddingValues ->

        LaunchedEffect(isConnectionAvailable) {
            if (!isConnectionAvailable) {
                launch {
                    snackbarHostState.showSnackbar(
                        message = "Anda sedang offline, silahkan cek koneksi internet Anda."
                    )
                }
            }
        }

        UniversityListContent(
            modifier = modifier.padding(paddingValues),
            viewModel::refresh,
            homeScreenUiState.isLoading,
            !isConnectionAvailable,
            listUniversities,
            searchText,
            viewModel::onSearchTextChange,
            isOnSearch,
            onSelectedUniversity = { selectedUniversity ->
                viewModel.setSelectedUniversity(selectedUniversity)
                navController.navigate(DETAIL)
            }
        )

    }
}

@Preview
@Composable
fun HomeScreenPreview(){
    HomeScreen(
        rememberNavController(),
        hiltViewModel()
    )
}