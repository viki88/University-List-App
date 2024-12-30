package com.vikination.universitylistapp.ui.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vikination.universitylistapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UniversityAppBar(
    onActionClick : () -> Unit,
    isOnSearch: Boolean,
){
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        title = {
            Text(text = stringResource(R.string.app_bar_title))
        },
        actions = {
            IconButton(
                onClick = onActionClick
            ) {
                Icon(
                    if(!isOnSearch) Icons.Filled.Search
                    else Icons.Filled.Close,
                    stringResource(R.string.desc_search),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview
@Composable
fun UniversityAppBarPreview(){
    UniversityAppBar({},true)
}