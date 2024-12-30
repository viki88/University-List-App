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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vikination.universitylistapp.ui.utils.UniversityAppBar
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onSearchClicked: () -> Unit,
    viewModel: UniversityViewModel = hiltViewModel(),
){
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        modifier = modifier.fillMaxWidth(),
        topBar = {
            UniversityAppBar(
                onSearchClicked
            )
        }
    ){ paddingValues ->
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
        )

    }
}