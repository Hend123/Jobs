package com.example.weatherforecast.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jobs.pojo.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow


class DatabaseHelperImpl (private val jobDatabase: JobDatabase) : DatabaseHelper{

    override suspend fun insertFavoriteJob(jobFav: Job)
    = jobDatabase.favoriteDao().insertFavoriteJob(jobFav)

    override  fun getFavoriteJobs(): Flow<MutableList<Job>>
    = jobDatabase.favoriteDao().getFavoriteJobs()

    override suspend fun deleteFavoriteJob(jobFav: Job)
    = jobDatabase.favoriteDao().deleteFavoriteJob(jobFav)

    override suspend fun updateFavJob(jobFav: Job)
    = jobDatabase.favoriteDao().updateFavJob(jobFav)

//    override  fun chechJobInDbOrNot(id_: String): LiveData<String>
//    = jobDatabase.favoriteDao().chechJobInDbOrNot(id_)


}