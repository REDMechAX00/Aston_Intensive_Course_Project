package com.redmechax00.astonintensivecourseproject.presentation.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.redmechax00.astonintensivecourseproject.R
import com.redmechax00.astonintensivecourseproject.databinding.ActivityMainBinding
import com.redmechax00.astonintensivecourseproject.presentation.screens.mainscreen.MainScreenFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            startMainScreenFragment()
        }
    }

    private fun startMainScreenFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, MainScreenFragment())
            .commit()
    }
}