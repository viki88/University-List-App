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
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val isOnSearch by viewModel.isOnSearch.collectAsStateWithLifecycle()

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

        LaunchedEffect(uiState.universities) {
            if (uiState.universities.isEmpty()) viewModel.refresh()
        }

        LaunchedEffect(uiState.isConnectionAvailable){
            if (!uiState.isConnectionAvailable){
                launch {
                    snackbarHostState.showSnackbar(
                        message = "Anda sedang offline, silahkan cek koneksi internet Anda."
                    )
                }
            }
        }

        LaunchedEffect(uiState.isLoading) {
            print("state is loading ${uiState.isLoading}")
        }

        UniversityListContent(
            modifier = modifier.padding(paddingValues),
            viewModel::refresh,
            uiState.isLoading,
            !uiState.isConnectionAvailable,
            uiState.universities,
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