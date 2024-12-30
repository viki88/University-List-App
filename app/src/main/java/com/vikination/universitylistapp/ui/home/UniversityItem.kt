package com.vikination.universitylistapp.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.vikination.universitylistapp.R
import com.vikination.universitylistapp.data.University

@Composable
fun UniversityItem(
    university: University,
    onSelectedUniversity: (University) -> Unit
){
    Card(Modifier.padding(8.dp)){
        Row (
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .clickable {
                    onSelectedUniversity(university)
                }
            ,
            verticalAlignment = Alignment.CenterVertically
        ){
            Box(
                Modifier.width(60.dp).height(60.dp).padding(8.dp)
            ) {
                AsyncImage(
                    model = R.drawable.img_university,
                    contentDescription = "Icon School",
                    colorFilter = ColorFilter.tint(iconThumbColor())
                )
            }
            Column {
                Text(
                    university.name,
                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    )
                )
                AnimatedVisibility(visible = university.province != "N/A") {
                    Text(
                        university.province,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Light
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun iconThumbColor() :Color{
    return if (isSystemInDarkTheme()) Color.White else MaterialTheme.colorScheme.inverseSurface
}

@Preview
@Composable
fun UniversityItemPreview(){
    UniversityItem(
        University("Telkom Unversity","Jawa Barat", "www.google.com"),
        {}
    )
}