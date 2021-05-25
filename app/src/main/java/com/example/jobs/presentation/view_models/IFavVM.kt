package com.example.jobs.presentation.view_models

import androidx.lifecycle.LiveData
import com.example.jobs.pojo.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface IFavVM {
    fun getFavoriteJobs(): Flow<MutableList<Job>>
}