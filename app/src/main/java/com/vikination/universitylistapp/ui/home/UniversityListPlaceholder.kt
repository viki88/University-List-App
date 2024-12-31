package com.vikination.universitylistapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer

@Composable
fun UniversityListPlaceholder(){
    LazyColumn {
        items(15){
            UniversityItemPlaceholder()
        }
    }
}

@Composable
fun UniversityItemPlaceholder(){
    Card(Modifier.padding(8.dp)){
        Row (
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
            ,
            verticalAlignment = Alignment.CenterVertically
        ){
            Box(
                Modifier
                    .shimmer()
                    .width(60.dp)
                    .height(60.dp)
                    .padding(8.dp)
                    .background(MaterialTheme.colorScheme.secondary)
            )
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Box(
                    modifier = Modifier
                        .shimmer()
                        .background(MaterialTheme.colorScheme.secondary)
                        .width(200.dp)
                ){
                    Spacer(modifier = Modifier.height(16.dp))
                }
                Box(
                    modifier = Modifier
                        .shimmer()
                        .background(MaterialTheme.colorScheme.secondary)
                        .width(100.dp)
                ){
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun UniversityListPlaceholderPreview(){
    UniversityListPlaceholder()
}