package com.example.noteappmvi.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ui.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.apply {
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}