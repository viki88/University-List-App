package com.vikination.universitylistapp.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vikination.universitylistapp.data.University

@Entity(tableName = "university")
data class LocalUniversity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var name: String,
    var stateProvince: String,
    var webPage: String
){
    companion object{
        fun getDummyLocalUniversities() = listOf(
            LocalUniversity(1,"Univ 1","Jawa Barat","www.google.com"),
            LocalUniversity(2,"Univ 2","Jawa Tengah","www.google.com"),
            LocalUniversity(3,"Univ 3","Jawa Timur","www.google.com"),
            LocalUniversity(4,"Univ 4","Jakarta","www.google.com"),
        )
    }
}