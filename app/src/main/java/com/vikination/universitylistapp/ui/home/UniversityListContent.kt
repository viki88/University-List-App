package com.vikination.universitylistapp.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vikination.universitylistapp.data.University

@Composable
fun UniversityListContent(
    modifier: Modifier,
    onRefresh: ()->Unit,
    listUniversity: List<University>
){
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextButton(
            onClick = onRefresh
        ) {
            Text("Refresh list")
        }
        LazyColumn {
            items(listUniversity){
                UniversityItem( it)
            }
        }
    }
}