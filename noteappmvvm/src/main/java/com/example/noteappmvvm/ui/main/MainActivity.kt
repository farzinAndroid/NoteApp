package com.example.noteappmvvm.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.noteappmvvm.ui.main.note.NoteFragment
import com.example.noteappmvvm.viewmodel.NoteViewmodel
import com.example.ui.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding


    //viewmodel
    private val viewModel by viewModels<NoteViewmodel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.apply {
            addNoteBtn.setOnClickListener {
                NoteFragment().show(supportFragmentManager, NoteFragment().tag)
            }


        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}