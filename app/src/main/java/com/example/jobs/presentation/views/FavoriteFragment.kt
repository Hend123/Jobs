package com.example.jobs.presentation.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobs.R
import com.example.jobs.Utils.view_model_factory.FavVMF
import com.example.jobs.Utils.view_model_factory.HomeVMF
import com.example.jobs.adapters.FavJobAdapter
import com.example.jobs.adapters.JobAdapter
import com.example.jobs.databinding.FragmentFavoriteBinding
import com.example.jobs.pojo.Job
import com.example.jobs.presentation.view_models.FavVM
import com.example.jobs.presentation.view_models.HomeVM
import com.example.retrofitandcoroutine.data.remote.RetrofitClient
import com.example.weatherforecast.data.local.DatabaseHelperImpl
import com.example.weatherforecast.data.local.JobDatabase
import com.example.weatherforecast.data.remote.ApiHelperImpl
import kotlinx.coroutines.flow.collect

class FavoriteFragment : Fragment(), FavJobAdapter.OnItemClickListener {
    private lateinit var fragmentFavoriteBinding: FragmentFavoriteBinding
    private lateinit var favVM: FavVM
    private lateinit var favJobAdapter: FavJobAdapter
    private lateinit var favJobList: MutableList<Job>

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
        fragmentFavoriteBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_favorite, container, false)
        init()
        lifecycleScope.launchWhenStarted {
            getFavJobFromDb()
        }

        return fragmentFavoriteBinding.root
    }
    private suspend fun getFavJobFromDb(){
        favVM.getFavoriteJobs().collect{
                initiRvFavJob(it)
            favJobList = it
        }

    }
    private fun initiRvFavJob(jobList: MutableList<Job>) {
        favJobAdapter.setDataAndContext(jobList, requireContext())
    }
    private fun init() {
        favVM =
            ViewModelProvider(
                this, FavVMF(
                    ApiHelperImpl(RetrofitClient.getApiService()), DatabaseHelperImpl(
                        JobDatabase.getInstance(requireContext())
                    )
                )
            ).get(
                FavVM::class.java
            )
        fragmentFavoriteBinding.favJobRv.setHasFixedSize(true)
        fragmentFavoriteBinding.favJobRv.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        favJobAdapter = FavJobAdapter()
        fragmentFavoriteBinding.favJobRv.adapter = favJobAdapter
        favJobAdapter.setOnItemClickListener(this)
        favJobList = ArrayList()
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            FavoriteFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onItemClick(position: Int) {
        val jobItem = bundleOf("jobItem" to favJobList.get(position))
        findNavController().navigate(
            R.id.action_favoriteFragment2_to_detailsFragment,
            jobItem
        )
    }
}