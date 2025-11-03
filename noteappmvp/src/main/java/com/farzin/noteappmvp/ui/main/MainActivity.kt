package com.farzin.noteappmvp.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.farzin.noteappmvp.data.models.NoteEntity
import com.farzin.noteappmvp.data.repository.main.MainRepository
import com.farzin.noteappmvp.databinding.ActivityMainBinding
import com.farzin.noteappmvp.ui.add.AddNoteFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() , MainContracts.View {

    private lateinit var binding:ActivityMainBinding


    //other

    @Inject
    lateinit var repository: MainRepository

    private val mainPresenter by lazy { MainPresenter(repository,this) }


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


            //load all notes
            mainPresenter.getAllNotes()
        }
    }

    override fun showAllNotes(notesList: List<NoteEntity>) {
        binding.emptySectionLayout.visibility = View.GONE
        binding.noteListRv.visibility = View.VISIBLE

        Toast.makeText(this, notesList.size.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun showEmptyList() {
        binding.emptySectionLayout.visibility = View.VISIBLE
        binding.noteListRv.visibility = View.GONE
    }
}