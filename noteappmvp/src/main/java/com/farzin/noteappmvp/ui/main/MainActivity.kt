package com.farzin.noteappmvp.ui.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.farzin.noteappmvp.R
import com.farzin.noteappmvp.databinding.ActivityMainBinding
import com.farzin.noteappmvp.ui.add.NoteFragment
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
                NoteFragment().show(supportFragmentManager,NoteFragment().tag)
            }
        }
    }
}