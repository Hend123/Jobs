package com.example.jobs.presentation.view_models

import androidx.lifecycle.MutableLiveData
import com.example.jobs.pojo.Job
import com.example.weatherforecast.utils.Resource
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface IHomeVM {
    fun fetchJobsApi(): MutableStateFlow<Resource<MutableList<Job>>>
    suspend fun addjobFavInDB(jobFav: Job)
    fun deleteFavJob(jobFav: Job)
    suspend fun updateFavJob(jobFav: Job)
    fun getFavoriteJobs(): Flow<MutableList<Job>>

    //fun chechJobInDbOrNot(id_: String): MutableLiveData<String>
}