package com.example.jobs.presentation.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.jobs.R
import com.example.jobs.Utils.view_model_factory.DetailsVMF
import com.example.jobs.Utils.view_model_factory.HomeVMF
import com.example.jobs.databinding.FragmentDetailsBinding
import com.example.jobs.pojo.Job
import com.example.jobs.presentation.view_models.DetailsVM
import com.example.jobs.presentation.view_models.HomeVM
import com.example.retrofitandcoroutine.data.remote.RetrofitClient
import com.example.weatherforecast.data.local.DatabaseHelperImpl
import com.example.weatherforecast.data.local.JobDatabase
import com.example.weatherforecast.data.remote.ApiHelperImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailsFragment : Fragment() {
  private lateinit var fragmentDetailsbinding: FragmentDetailsBinding
    private var favoriteItem: Job? = null
    private lateinit var detailsVM:DetailsVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            favoriteItem = it.getParcelable<Job>("jobItem")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentDetailsbinding = DataBindingUtil.inflate(inflater,R.layout.fragment_details, container, false)
        init()

        fragmentDetailsbinding.favBtnDetails.setOnClickListener{
            handleClickBtn()
        }
        return fragmentDetailsbinding.root
    }
    private fun init(){
        fragmentDetailsbinding.favJobItem = favoriteItem
        detailsVM = ViewModelProvider(
            this, DetailsVMF(
                ApiHelperImpl(RetrofitClient.getApiService()), DatabaseHelperImpl(
                    JobDatabase.getInstance(requireContext())
                )
            )
        ).get(
            DetailsVM::class.java
        )

    }
    private fun handleClickBtn(){
        fragmentDetailsbinding.favBtnDetails.isSelected = !fragmentDetailsbinding.favBtnDetails.isSelected
        if (fragmentDetailsbinding.favBtnDetails.isSelected) {
            Log.v("statusFavBtn", "true")
            GlobalScope.launch {
                Dispatchers.IO
                detailsVM.addjobFavInDB(favoriteItem!!)
                favoriteItem!!.favorite = "fav"
                detailsVM.updateFavJob(favoriteItem!!)
            }
        } else {
            Log.v("statusFavBtn", "false")
            detailsVM.deleteFavJob(favoriteItem!!)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            DetailsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}