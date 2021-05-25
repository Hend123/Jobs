package com.example.jobs.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jobs.pojo.Job
import com.example.weatherforecast.utils.Resource
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface IRepo {
    fun fetchJobsApi(): MutableStateFlow<Resource<MutableList<Job>>>
    suspend fun addjobFavInDB(jobFav: Job)
    fun getFavoriteJobs(): Flow<MutableList<Job>>
    fun deleteFavJob(jobFav: Job)
    suspend fun updateFavJob(jobFav: Job)
    //fun chechJobInDbOrNot(id_:String):LiveData<String>
}