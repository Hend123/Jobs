package com.example.jobs.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ToggleButton
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.jobs.R
import com.example.jobs.Utils.view_model_factory.HomeVMF
import com.example.jobs.databinding.JobItemBinding
import com.example.jobs.pojo.Job
import com.example.jobs.presentation.view_models.HomeVM
import com.example.retrofitandcoroutine.data.remote.RetrofitClient
import com.example.weatherforecast.data.local.DatabaseHelperImpl
import com.example.weatherforecast.data.local.JobDatabase
import com.example.weatherforecast.data.remote.ApiHelperImpl

class JobAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var jobItemBinding: JobItemBinding
    private var jobList: MutableList<Job>
    private lateinit var context: Context
    private lateinit var onItemClickListener: OnItemClickListener

    init {
        jobList = ArrayList()


    }

    /*
    SetUp Of Delete Item
     */
    fun getJobList(): MutableList<Job> = jobList
    fun getItemByVH(viewHolder: RecyclerView.ViewHolder): Job {
        return jobList.get(viewHolder.adapterPosition)
    }

    fun removeFavoriteItem(viewHolder: RecyclerView.ViewHolder) {
        jobList.removeAt(viewHolder.adapterPosition)
        notifyItemRemoved(viewHolder.adapterPosition)

    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onFavBtnClick(position: Int, imageButton: ImageButton)
    }

    fun setOnItemClickListener(onItemClickListLener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListLener
    }

    fun setDataAndContext(jobList: MutableList<Job>, context: Context) {
        this.jobList = jobList
        this.context = context
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        jobItemBinding = DataBindingUtil.inflate(inflater, R.layout.job_item, parent, false)
        return JobItemsViewHolder(jobItemBinding)
    }

    override fun getItemCount(): Int {
        return if (jobList.size == 0) 0 else jobList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as JobItemsViewHolder

//        if (jobList[position].favorite!= null && jobList[position].favorite.equals("fav")) {
//            Log.v("favBtnValue",jobList[position].favorite + "hello")
//            viewHolder.binding.favBtn.isSelected = viewHolder.binding.favBtn.isSelected
//        } else {
//            viewHolder.binding.favBtn.isSelected = !viewHolder.binding.favBtn.isSelected
//        }
        viewHolder.bind(jobList[position])
    }

    inner class JobItemsViewHolder(val binding: JobItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bind(item: Job) {
            binding.jobItem = item
            binding.root.setOnClickListener(this)
            binding.root.setClickable(true)
            binding.favBtn.setOnClickListener(this)
            binding.favBtn.setClickable(true)
            Log.v("favBtnValue",item.favorite + "hello")
            if (item.favorite!= null && item.favorite.equals("fav")) {
                Log.v("favBtnValue",item.favorite + "hello")
                binding.favBtn.isSelected = binding.favBtn.isSelected
            } else if (item.favorite!= null && item.favorite.equals("unfav")) {
                binding.favBtn.isSelected = !binding.favBtn.isSelected
            }
            binding.executePendingBindings()
        }

        override fun onClick(v: View?) {
            if (v == binding.root) {
                onItemClickListener.onItemClick(adapterPosition)
            }
            if (v == binding.favBtn) {
                onItemClickListener.onFavBtnClick(adapterPosition, binding.favBtn)
            }

        }
    }
}