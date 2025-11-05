package com.farzin.noteappmvp.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
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

    @Inject
    lateinit var mainPresenter: MainPresenter

//    private val mainPresenter by lazy { MainPresenter(repository,this) }

    @Inject
    lateinit var notesAdapter: NotesListAdapter


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

            /*notesAdapter.setOnClickListener { noteEntity->
                Toast.makeText(this@MainActivity, noteEntity.title, Toast.LENGTH_SHORT).show()
            }*/
        }
    }

    override fun showAllNotes(notesList: List<NoteEntity>) {
        binding.emptySectionLayout.visibility = View.GONE
        binding.noteListRv.visibility = View.VISIBLE

        notesAdapter.setData(notesList)


        binding.noteListRv.apply {
            layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            adapter = notesAdapter
        }
    }

    override fun showEmptyList() {
        binding.emptySectionLayout.visibility = View.VISIBLE
        binding.noteListRv.visibility = View.GONE
    }
}