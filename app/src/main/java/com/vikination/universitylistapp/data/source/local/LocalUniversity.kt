package com.vikination.universitylistapp.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "university")
data class LocalUniversity(
    @PrimaryKey val id: String,
    var name: String,
    var stateProvince: String,
    var webPage: String
)