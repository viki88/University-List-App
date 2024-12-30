package com.vikination.universitylistapp.ui.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vikination.universitylistapp.ui.utils.UniversityAppBar

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onSearchClicked: () -> Unit,
    viewModel: UniversityViewModel = hiltViewModel()
){
    Scaffold(
        modifier = modifier.fillMaxWidth(),
        topBar = {
            UniversityAppBar(
                onSearchClicked
            )
        }
    ){ paddingValues ->
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        println(
            "list universities ${uiState.universities}"
        )

        UniversityListContent(
            modifier = modifier.padding(paddingValues),
            viewModel::refresh,
            uiState.universities
        )
    }
}