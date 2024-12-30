package com.vikination.universitylistapp.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vikination.universitylistapp.R
import com.vikination.universitylistapp.data.University

@Composable
fun UniversityItem(
    university: University
){
    Card(Modifier.padding(8.dp)){
        Row (
            modifier = Modifier.padding(8.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Box(
                Modifier.width(60.dp).height(60.dp).padding(8.dp)
            ) {
                Image(painterResource(R.drawable.img_university), contentDescription = "Icon School")
            }
            Column {
                Text(university.name)
                Text(university.province)
            }
        }
    }
}