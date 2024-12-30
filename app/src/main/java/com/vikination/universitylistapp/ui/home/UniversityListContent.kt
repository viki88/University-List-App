package com.vikination.universitylistapp.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vikination.universitylistapp.R
import com.vikination.universitylistapp.data.University
import com.vikination.universitylistapp.data.sortByName

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UniversityListContent(
    modifier: Modifier,
    onRefresh: ()->Unit,
    isLoading: Boolean,
    isOfflineMode: Boolean,
    listUniversity: List<University>,
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    isOnSearch: Boolean
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
        AnimatedVisibility(
            visible = isOnSearch
        ) {
            Box(
                modifier = Modifier.background(MaterialTheme.colorScheme.primary)
            ){
                TextField(
                    placeholder = {
                        Text(
                            stringResource(R.string.hint_search_bar),
                            Modifier.alpha(60F)
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    leadingIcon = {
                        Icon(Icons.Filled.Search, "Search")
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                onSearchTextChange("")
                            }
                        ) {
                            Icon(Icons.Filled.Clear, "Clear")
                        }
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    shape = RoundedCornerShape(32.dp),
                    value = searchText,
                    onValueChange = onSearchTextChange
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