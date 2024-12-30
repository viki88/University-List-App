package com.vikination.universitylistapp.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "university")
data class LocalUniversity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var name: String,
    var stateProvince: String,
    var webPage: String
)