package com.example.jobs.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jobs.pojo.Job
import com.example.weatherforecast.data.local.DatabaseHelper
import com.example.weatherforecast.data.remote.ApiHelper
import com.example.weatherforecast.utils.Resource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class Repo() : IRepo {
    private var jobsMutableStateFlowApi: MutableStateFlow<Resource<MutableList<Job>>>
    private var mApiHelper: ApiHelper? = null
    private var mDbHelper: DatabaseHelper? = null
    private var favoriteStateFlow:MutableStateFlow<String>


    constructor(apiHelper: ApiHelper, dbHelper: DatabaseHelper) : this() {
        this.mApiHelper = apiHelper
        this.mDbHelper = dbHelper
    }

    init {
        jobsMutableStateFlowApi = MutableStateFlow<Resource<MutableList<Job>>>(Resource.loading(null))
        favoriteStateFlow = MutableStateFlow<String>(" ")
    }

    override fun fetchJobsApi(): MutableStateFlow<Resource<MutableList<Job>>> {
        GlobalScope.launch {
            Dispatchers.IO
            jobsMutableStateFlowApi.emit(Resource.loading(null))
            try {
                val response = mApiHelper!!.getJobs("api")
                withContext(Dispatchers.Main) {
                jobsMutableStateFlowApi.emit(Resource.success(response))
                }
            } catch (e: Exception) {
                Log.e("errorApi",e.toString() + "jjj")
                jobsMutableStateFlowApi.emit(Resource.error(e.toString(), null))
            }
        }
        return jobsMutableStateFlowApi

    }
    override suspend fun addjobFavInDB(jobFav: Job) {

        GlobalScope.launch(Dispatchers.IO) {
            mDbHelper!!.insertFavoriteJob(jobFav)
            withContext(Dispatchers.Main){
                Log.v("jobFav",jobFav.company + "lll")
            }
        }
  }
    override fun getFavoriteJobs(): Flow<MutableList<Job>> {
        return mDbHelper!!.getFavoriteJobs()
    }
    override fun deleteFavJob(jobFav: Job){
        Log.v("delete",jobFav.title!!)
        GlobalScope.launch {
            Dispatchers.IO
            mDbHelper!!.deleteFavoriteJob(jobFav)
        }
    }

    override suspend fun updateFavJob(jobFav: Job) {
        mDbHelper!!.updateFavJob(jobFav)
    }

//    override fun chechJobInDbOrNot(id_: String): LiveData<String> {
//        GlobalScope.launch {
//            Dispatchers.IO
//            val response = mDbHelper!!.chechJobInDbOrNot(id_)
//            withContext(Dispatchers.Main){
//               favoriteLiveData = response
//            }
//        }
//        return favoriteLiveData
//    }

}