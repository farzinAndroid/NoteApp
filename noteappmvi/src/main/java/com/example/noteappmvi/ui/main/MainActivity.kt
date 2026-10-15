package com.example.noteappmvi.ui.main

import android.os.Bundle
import android.view.Menu
import android.widget.GridLayout.VERTICAL
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.local.Constants
import com.example.noteappmvi.data.model.NoteEntity
import com.example.noteappmvi.ui.main.note.NoteFragment
import com.example.noteappmvi.ui.main.note.NotesListAdapter
import com.example.noteappmvi.viewmodel.main.MainIntent
import com.example.noteappmvi.viewmodel.main.MainState
import com.example.noteappmvi.viewmodel.main.MainViewmodel
import com.example.ui.R
import com.example.ui.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding


    @Inject
    lateinit var noteAdapter: NotesListAdapter

    @Inject
    lateinit var noteEntity: NoteEntity

    private val viewmodel : MainViewmodel by viewModels<MainViewmodel>()

    private var selectedItem = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.apply {
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

            //add
            addNoteBtn.setOnClickListener {
                NoteFragment().show(supportFragmentManager, NoteFragment().tag)
            }

            noteAdapter.setOnClickListener { entity, type ->
                when(type){
                    Constants.EDIT ->{
                        viewmodel.handleIntent(MainIntent.GoToDetail(entity.id))

                    }

                    Constants.DELETE->{
                        noteEntity.id = entity.id
                        noteEntity.title = entity.title
                        noteEntity.desc = entity.desc
                        noteEntity.category = entity.category
                        noteEntity.priority = entity.priority

                        viewmodel.handleIntent(MainIntent.DeleteNote(noteEntity))
                    }
                }
            }

            viewmodel.handleIntent(MainIntent.LoadAllNotes)
            lifecycleScope.launch {
                viewmodel.state.collectLatest {
                    when(it){
                        is MainState.ShowNotes -> {
                            emptySectionLayout.isVisible = false
                            noteListRv.isVisible = true
                            noteAdapter.setData(it.noteList)
                            noteListRv.apply {
                                layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
                                adapter = noteAdapter
                            }
                        }
                        MainState.ShowEmptyScreen -> {
                            emptySectionLayout.isVisible = true
                            noteListRv.isVisible = false
                        }

                        is MainState.DeleteNote -> {
                            Toast.makeText(this@MainActivity, "Deleted", Toast.LENGTH_SHORT).show()
                        }

                        is MainState.OpenDetail -> {
                            val noteFragment = NoteFragment()
                            val bundle = Bundle()
                            bundle.putInt(Constants.BUNDLE_ID,it.id)
                            noteFragment.arguments = bundle
                            noteFragment.show(supportFragmentManager,noteFragment.tag)
                        }
                    }
                }
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
                viewmodel.handleIntent(MainIntent.SearchNote(newText))
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
                viewmodel.handleIntent(MainIntent.LoadAllNotes)
            }else{
                viewmodel.handleIntent(MainIntent.FilterNote(priorities[item]))
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