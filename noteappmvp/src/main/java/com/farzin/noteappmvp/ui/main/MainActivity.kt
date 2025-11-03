package com.farzin.noteappmvp.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.farzin.noteappmvp.databinding.ActivityMainBinding
import com.farzin.noteappmvp.ui.add.AddNoteFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            //noteDetail
            addNoteBtn.setOnClickListener {
                AddNoteFragment().show(supportFragmentManager,AddNoteFragment().tag)
            }
        }
    }
}