package com.example.weatherforecast.data.remote

import com.example.jobs.pojo.Job
import com.example.retrofitandcoroutine.data.remote.ApiService
import kotlinx.coroutines.Deferred
import retrofit2.Call

class ApiHelperImpl(private val apiService: ApiService): ApiHelper  {
    override suspend fun getJobs(description:String): MutableList<Job> {
       return apiService.getJobs(description)
    }
}