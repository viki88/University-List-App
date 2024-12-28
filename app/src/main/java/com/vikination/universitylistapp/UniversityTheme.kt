package com.vikination.universitylistapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun UniversityTheme(content: @Composable ()-> Unit){
    MaterialTheme(
        lightColorScheme(
            primary = Color(0xFF263238),
            secondary = Color(0xFF2E7D32),
            tertiary = Color(0xFFCCCCCC)
        )
    ){
        content()
    }
}