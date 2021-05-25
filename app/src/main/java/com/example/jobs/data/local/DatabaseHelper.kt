package com.example.weatherforecast.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jobs.pojo.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow


interface DatabaseHelper {
  suspend fun insertFavoriteJob(jobFav: Job)

    fun getFavoriteJobs(): Flow<MutableList<Job>>

    suspend fun deleteFavoriteJob(jobFav: Job)
  suspend fun updateFavJob(jobFav: Job)
   //fun chechJobInDbOrNot(id_:String):LiveData<String>
}