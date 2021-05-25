package com.example.jobs.presentation.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.jobs.R
import com.example.jobs.databinding.ActivityJobsBinding

class JobsActivity : AppCompatActivity() {
   private lateinit var jobsActivityBinding: ActivityJobsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        jobsActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_jobs)
        setupNav()
    }
    private fun setupNav() {
        val navController = findNavController(R.id.fragment)
        jobsActivityBinding.bottomNavView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment2 -> showBottomNav()
                R.id.favoriteFragment2 -> showBottomNav()
                else -> hideBottomNav()
            }
        }
    }

    private fun showBottomNav() {
        jobsActivityBinding.bottomNavView.visibility = View.VISIBLE


    }

    private fun hideBottomNav() {
        jobsActivityBinding.bottomNavView.visibility = View.GONE

    }
}