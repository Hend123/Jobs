package com.example.jobs.presentation.view_models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jobs.pojo.Job
import com.example.jobs.repository.Repo
import com.example.weatherforecast.data.local.DatabaseHelper
import com.example.weatherforecast.data.remote.ApiHelper
import com.example.weatherforecast.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class HomeVM(apiHelper: ApiHelper, dbHelper: DatabaseHelper) : ViewModel(), IHomeVM {
    private var repo: Repo

    //var jobFavMutableLiveDataDB: LiveData<Job>
    var jobsMutableStateFlowApi: MutableStateFlow<Resource<MutableList<Job>>>
     var favoriteStateFlow:MutableStateFlow<String>


    init {
        repo = Repo(apiHelper, dbHelper)
        jobsMutableStateFlowApi =MutableStateFlow<Resource<MutableList<Job>>>(Resource.loading(null))
        favoriteStateFlow = MutableStateFlow<String>(" ")
        // jobFavMutableLiveDataDB = MutableLiveData()
        jobsMutableStateFlowApi = repo.fetchJobsApi()
    }

    override fun fetchJobsApi(): MutableStateFlow<Resource<MutableList<Job>>> {
        return jobsMutableStateFlowApi
    }

    override suspend fun addjobFavInDB(jobFav: Job) {
        repo.addjobFavInDB(jobFav)
    }

    override fun deleteFavJob(jobFav: Job) {
        repo.deleteFavJob(jobFav)
    }

    override suspend fun updateFavJob(jobFav: Job) {
        repo.updateFavJob(jobFav)
    }

    override fun getFavoriteJobs(): Flow<MutableList<Job>> {
        return repo.getFavoriteJobs()
    }

//    override  fun chechJobInDbOrNot(id_: String): MutableLiveData<String> {
//        favoriteLiveData = repo. chechJobInDbOrNot(id_)
//        return favoriteLiveData
//    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed!")
    }
}