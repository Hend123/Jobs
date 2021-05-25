package com.example.jobs.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.jobs.pojo.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow


@Dao
interface FavoriteDao {
    /**
     * Dao of  favorite job
     */
    // @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Insert
    suspend fun insertFavoriteJob(jobFav: Job)

    @Query("select * from job_favorite_table")
    fun getFavoriteJobs(): Flow<MutableList<Job>>


    @Delete
    suspend fun deleteFavoriteJob(jobFav: Job)

    @Update
    suspend fun updateFavJob(jobFav: Job)

//    @Query("select id from job_favorite_table where id = :id_")
//    fun chechJobInDbOrNot(id_: String):LiveData<String>
}