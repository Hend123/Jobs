package com.example.jobs.Utils.view_model_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jobs.presentation.view_models.HomeVM
import com.example.weatherforecast.data.local.DatabaseHelper
import com.example.weatherforecast.data.remote.ApiHelper


@Suppress("UNCHECKED_CAST")
class HomeVMF(private val apiHelper: ApiHelper, private val dbHelper: DatabaseHelper) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(HomeVM::class.java)) {
            return HomeVM(apiHelper,dbHelper) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}