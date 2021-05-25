package com.example.jobs.presentation.view_models

import androidx.lifecycle.ViewModel
import com.example.jobs.pojo.Job
import com.example.jobs.repository.Repo
import com.example.weatherforecast.data.local.DatabaseHelper
import com.example.weatherforecast.data.remote.ApiHelper

class DetailsVM(apiHelper: ApiHelper, dbHelper: DatabaseHelper): ViewModel(), IDetailsVM {
    private var repo: Repo
    init {
        repo = Repo(apiHelper, dbHelper)
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
}