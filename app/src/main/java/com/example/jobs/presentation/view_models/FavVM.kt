package com.example.jobs.presentation.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jobs.pojo.Job
import com.example.jobs.repository.Repo
import com.example.weatherforecast.data.local.DatabaseHelper
import com.example.weatherforecast.data.remote.ApiHelper
import com.example.weatherforecast.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FavVM(apiHelper: ApiHelper, dbHelper: DatabaseHelper) : ViewModel(), IFavVM {
    private var repo: Repo

    init {
        repo = Repo(apiHelper, dbHelper)
    }

    override fun getFavoriteJobs(): Flow<MutableList<Job>> {
        return repo.getFavoriteJobs()
    }

}