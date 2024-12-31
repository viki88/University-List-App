package com.vikination.universitylistapp.ui.detail

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.vikination.universitylistapp.ui.home.UniversityViewModel
import com.vikination.universitylistapp.ui.utils.DetailAppBar

@Composable
fun DetailScreen(
    navController: NavController,
    viewModel: UniversityViewModel
) {
    val selectedUniversity by viewModel.selectedUniversity.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            DetailAppBar(
                onBackPress = {
                    navController.popBackStack()
                },
                selectedUniversity.name
            )
        }
    ) { paddingValues ->
        WebViewContent(
            selectedUniversity.webPage,
            Modifier.padding(paddingValues)
        )
    }
}

@Preview
@Composable
fun DetailScreenPreview(){
    DetailScreen(
        rememberNavController(),
        hiltViewModel()
    )
}