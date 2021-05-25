package com.example.retrofitandcoroutine.data.remote




import com.example.jobs.pojo.Job
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("positions.json")
   suspend  fun getJobs(@Query("description") description:String): MutableList<Job>

}