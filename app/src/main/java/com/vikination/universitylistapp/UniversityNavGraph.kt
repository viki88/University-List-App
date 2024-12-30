package com.vikination.universitylistapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vikination.universitylistapp.ui.home.HomeScreen

@Composable
fun UniversityNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
){

    NavHost(
        navController = navController,
        startDestination = HOME,
        modifier = modifier
    ) {
        composable<HOME> {
            HomeScreen()
        }
    }

}