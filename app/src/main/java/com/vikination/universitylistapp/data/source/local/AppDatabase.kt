package com.vikination.universitylistapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LocalUniversity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun universityDao(): UniversityDao
}