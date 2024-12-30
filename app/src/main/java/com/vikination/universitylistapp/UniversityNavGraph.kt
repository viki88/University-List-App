package com.vikination.universitylistapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vikination.universitylistapp.ui.detail.DetailScreen
import com.vikination.universitylistapp.ui.home.HomeScreen
import com.vikination.universitylistapp.ui.home.UniversityViewModel

@Composable
fun UniversityNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: UniversityViewModel = hiltViewModel()
){

    NavHost(
        navController = navController,
        startDestination = HOME,
        modifier = modifier
    ) {
        composable<HOME> {
            HomeScreen(
                navController,
                viewModel
            )
        }
        composable<DETAIL> {
            DetailScreen(
                navController,
                viewModel
            )
        }
    }

}