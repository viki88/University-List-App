package com.vikination.universitylistapp.data.source.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface UniversityDao {

    /**
     * Observe list of universities
     * @return all universities
     */
    @Query("SELECT * FROM university")
    fun observeAllUniversities(): Flow<List<LocalUniversity>>

    /**
     * Select all universities from table universities
     * @return all universities
     */
    @Query("SELECT * FROM university")
    suspend fun getAllUniversities(): List<LocalUniversity>

    /**
     * Insert and Update University in database
     * @param university the university to be inserted or updated
     */
    @Upsert
    suspend fun upsert(university: LocalUniversity)

    /**
     * Insert and Update universities in database
     * @param universities the universities to be inserted or updated
     */
    @Upsert
    suspend fun upsertAll(universities: List<LocalUniversity>)

    /**
     * Delete all universities
     */
    @Query("DELETE from university")
    suspend fun deleteAll()

}