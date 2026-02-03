package com.example.noteappmvvm.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import com.example.local.Constants
import com.example.noteappmvvm.data.model.NoteEntity
import com.example.noteappmvvm.ui.main.note.NoteFragment
import com.example.noteappmvvm.ui.main.note.NotesListAdapter
import com.example.noteappmvvm.viewmodel.MainViewmodel
import com.example.ui.R
import com.example.ui.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding


    //viewmodel
    private val viewModel by viewModels<MainViewmodel>()

    //adapter
    @Inject
    lateinit var notesListAdapter: NotesListAdapter

    private var selectedItem = 0

    @Inject
    lateinit var noteEntity: NoteEntity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.apply {

            //toolbar
            setSupportActionBar(notesToolbar)
            notesToolbar.setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener {
                when(it.itemId){
                    R.id.search->{
                        return@OnMenuItemClickListener false
                    }
                    R.id.filter->{
                        showPriorityAlertDialogue()
                        return@OnMenuItemClickListener true
                    }
                }
                return@OnMenuItemClickListener true
            })


            addNoteBtn.setOnClickListener {
                NoteFragment().show(supportFragmentManager, NoteFragment().tag)
            }

            //getNotes
            viewModel.getAllNotes()
            viewModel.allNotes.observe(this@MainActivity){
                showEmpty(it.isEmpty)
                    notesListAdapter.setData(it.data!!)
                    noteListRv.apply {
                        this.adapter = notesListAdapter
                        layoutManager = GridLayoutManager(this@MainActivity, 2)
                    }
                }

            //delete and edit
            notesListAdapter.setOnClickListener{entity,state->
                when(state){
                    Constants.DELETE->{
                        noteEntity = entity
                        viewModel.deleteNote(noteEntity)
                    }
                    Constants.EDIT ->{
                        val bundle = Bundle()
                        bundle.putInt(Constants.BUNDLE_ID,entity.id)
                        val noteFragment = NoteFragment()
                        noteFragment.arguments = bundle
                        noteFragment.show(supportFragmentManager, NoteFragment().tag)
                    }
                }
            }

        }


    }

    fun showEmpty(isShown: Boolean){
        binding?.apply {
            if (isShown){
                emptySectionLayout.visibility = View.VISIBLE
                noteListRv.visibility = View.GONE
            }else{
                emptySectionLayout.visibility = View.GONE
                noteListRv.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar,menu)

        val search = menu.findItem(R.id.search)

        val searchView = search.actionView as androidx.appcompat.widget.SearchView
        searchView.queryHint = this@MainActivity.getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.getSearchedNotes(newText)
                return true
            }

        })

        return super.onCreateOptionsMenu(menu)
    }



    private fun showPriorityAlertDialogue(){
        val builder = AlertDialog.Builder(this@MainActivity)

        val priorities = arrayOf(Constants.ALL,Constants.HIGH,Constants.MEDIUM,Constants.LOW)

        builder.setSingleChoiceItems(priorities,selectedItem){dialog,item->
            if (item == 0){
                viewModel.getAllNotes()
            }else{
                viewModel.getFilteredNotes(priorities[item])
            }
            selectedItem = item
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}