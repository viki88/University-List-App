package com.vikination.universitylistapp.ui.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vikination.universitylistapp.ui.utils.UniversityAppBar

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onSearchClicked: () -> Unit
){
    Scaffold(
        modifier = modifier.fillMaxWidth(),
        topBar = {
            UniversityAppBar(
                onSearchClicked
            )
        }
    ){ paddingValues ->
        UniversityListContent(modifier = modifier.padding(paddingValues))
    }
}