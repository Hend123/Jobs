package com.example.weatherforecast.data.remote

import com.example.jobs.pojo.Job
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.Query

interface ApiHelper {
    suspend fun getJobs(description:String): MutableList<Job>
}