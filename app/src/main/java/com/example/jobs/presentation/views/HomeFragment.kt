package com.example.jobs.presentation.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobs.R
import com.example.jobs.Utils.CheckInternet
import com.example.jobs.Utils.view_model_factory.HomeVMF
import com.example.jobs.adapters.JobAdapter
import com.example.jobs.databinding.FragmentHomeBinding
import com.example.jobs.pojo.Job
import com.example.jobs.presentation.view_models.HomeVM
import com.example.retrofitandcoroutine.data.remote.RetrofitClient
import com.example.weatherforecast.data.local.DatabaseHelperImpl
import com.example.weatherforecast.data.local.JobDatabase
import com.example.weatherforecast.data.remote.ApiHelperImpl
import com.example.weatherforecast.utils.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class HomeFragment : Fragment(), JobAdapter.OnItemClickListener {
    private lateinit var homeFragmentbinding: FragmentHomeBinding
    private lateinit var homeVM: HomeVM
    private lateinit var jobAdapter: JobAdapter
    private lateinit var jobList: MutableList<Job>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        homeFragmentbinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        homeFragmentbinding.lifecycleOwner = viewLifecycleOwner
        init()
        checkInternet()
        return homeFragmentbinding.root
    }

    private fun checkInternet() {
        if (CheckInternet.hasNetworkAvailable(requireContext())) {
           lifecycleScope.launchWhenStarted {
               observeDataFromApi()
           }

        } else {
            Toast.makeText(context, "Please check your internet", Toast.LENGTH_LONG).show()
        }
    }

    private suspend fun observeDataFromApi() {
        homeVM.fetchJobsApi()

        homeVM.jobsMutableStateFlowApi.collect {
            it?.let {
                when (it.status) {
                    Status.SUCCESS -> {
                        Log.v("status", "success")
                        it.data?.let {
                            Log.v("data", it.toString())
                            jobList = it
                            initiRvJob(it)
                        }

                    }
                    Status.LOADING -> {
                        Log.v("status", "loading")
                    }
                    Status.ERROR -> {
                        Log.v("status", "error")
                        Toast.makeText(requireContext(), "Connection Error", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }

    }

    private fun init() {
        homeVM =
            ViewModelProvider(
                this, HomeVMF(
                    ApiHelperImpl(RetrofitClient.getApiService()), DatabaseHelperImpl(
                        JobDatabase.getInstance(requireContext())
                    )
                )
            ).get(
                HomeVM::class.java
            )
        homeFragmentbinding.jobRv.setHasFixedSize(true)
        homeFragmentbinding.jobRv.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        jobAdapter = JobAdapter()
        homeFragmentbinding.jobRv.adapter = jobAdapter
        jobAdapter.setOnItemClickListener(this)
        jobList = ArrayList()
    }

    private fun initiRvJob(jobList: MutableList<Job>) {
        jobAdapter.setDataAndContext(jobList, requireContext())
    }


    companion object {

        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onItemClick(position: Int) {
        val jobItem = bundleOf("jobItem" to jobList.get(position))
        findNavController().navigate(
            R.id.action_homeFragment2_to_detailsFragment,
            jobItem
        )
    }

    override fun onFavBtnClick(position: Int, imageButton: ImageButton) {
        imageButton.isSelected = !imageButton.isSelected
        if (imageButton.isSelected) {
            Log.v("statusFavBtn", "true")
            GlobalScope.launch {
                Dispatchers.IO
                homeVM.addjobFavInDB(jobList.get(position))
                jobList.get(position).favorite = "fav"
                homeVM.updateFavJob(jobList.get(position))
            }
        } else {
            Log.v("statusFavBtn", "false")
            homeVM.deleteFavJob(jobList.get(position))
//            jobList.get(position).favorite = "unfav"
//            GlobalScope.launch {
//                Dispatchers.IO
//                homeVM.updateFavJob(jobList.get(position))
//            }
        }


    }


}