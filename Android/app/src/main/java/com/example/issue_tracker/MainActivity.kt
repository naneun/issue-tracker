package com.example.issue_tracker

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.issue_tracker.databinding.ActivityMainBinding
import com.example.issue_tracker.ui.HomeViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        setContentView(binding.root)
        setupNav()
    }

    private fun setupNav() {
        val navController =
            supportFragmentManager.findFragmentById(R.id.fragment_container)?.findNavController()

        navController?.let {
            binding.bottomNavigationView.setupWithNavController(it)
        }

        navController?.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.mileStoneWriteFragment -> binding.bottomNavigationView.menu[2].isChecked = true
                R.id.labelWriteFragment -> binding.bottomNavigationView.menu[1].isChecked = true
                R.id.issueWriteFragment -> binding.bottomNavigationView.menu[0].isChecked = true
            }
        }
    }

    private fun showBottomNav() {
        binding.bottomNavigationView.isVisible = true
    }

    private fun hideBottomNav() {
        binding.bottomNavigationView.isVisible = false
    }
}