package com.example.jobs.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.jobs.R
import com.example.jobs.databinding.FavJobItemBinding
import com.example.jobs.databinding.JobItemBinding
import com.example.jobs.pojo.Job

class FavJobAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var favJobItemBinding: FavJobItemBinding
    private var favJobList: MutableList<Job>
    private lateinit var context: Context
    private lateinit var onItemClickListener: OnItemClickListener

    init {
        favJobList = ArrayList()
    }


    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(onItemClickListLener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListLener
    }

    fun setDataAndContext(jobList: MutableList<Job>, context: Context) {
        this.favJobList = jobList
        this.context = context
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        favJobItemBinding = DataBindingUtil.inflate(inflater, R.layout.fav_job_item, parent, false)
        return FavJobItemsViewHolder(favJobItemBinding)
    }

    override fun getItemCount(): Int {
        return if (favJobList.size == 0) 0 else favJobList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as FavJobItemsViewHolder
        viewHolder.bind(favJobList[position])
    }

    inner class FavJobItemsViewHolder(val binding: FavJobItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bind(item: Job) {
            binding.jobItem = item
            binding.root.setOnClickListener(this)
            binding.root.setClickable(true)
            binding.executePendingBindings()
        }

        override fun onClick(v: View?) {
            if (v == binding.root) {
                onItemClickListener.onItemClick(adapterPosition)
            }
        }
    }
}