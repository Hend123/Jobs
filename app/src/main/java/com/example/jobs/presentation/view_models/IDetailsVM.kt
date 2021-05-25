package com.example.jobs.presentation.view_models

import com.example.jobs.pojo.Job

interface IDetailsVM {
    suspend fun addjobFavInDB(jobFav: Job)
    fun deleteFavJob(jobFav: Job)
    suspend fun updateFavJob(jobFav: Job)
}