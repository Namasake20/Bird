package com.namasake.animal

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.namasake.animal.databinding.ActivityMainBinding
import com.namasake.animal.presentation.BirdAdapter
import com.namasake.animal.presentation.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val birdAdapter = BirdAdapter()

        lifecycleScope.launchWhenStarted {
            viewModel.getAnimals()
            viewModel.dataState.collect { event ->
                when(event){
                    is MainViewModel.AnimalEvent.Success ->
                        birdAdapter.submitList(event.result)
                    is MainViewModel.AnimalEvent.Failure ->
                        Log.e(TAG,"something wrong")

                }
            }
        }
    }
}