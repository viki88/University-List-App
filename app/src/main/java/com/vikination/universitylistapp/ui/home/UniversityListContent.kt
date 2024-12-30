package com.vikination.universitylistapp.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vikination.universitylistapp.data.University
import com.vikination.universitylistapp.data.sortByName

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UniversityListContent(
    modifier: Modifier,
    onRefresh: ()->Unit,
    isLoading: Boolean,
    isOfflineMode: Boolean,
    listUniversity: List<University>
){
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = isOfflineMode
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.errorContainer)
                    .padding(4.dp)
            ) {
                Text(
                    text = "Offline mode",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = Modifier.padding(4.dp).align(Alignment.Center)
                )
            }
        }
        PullToRefreshBox(
            onRefresh = onRefresh,
            isRefreshing = isLoading
        ) {
            LazyColumn {
                items(listUniversity.sortByName()){
                    UniversityItem( it)
                }
            }
        }
    }
}