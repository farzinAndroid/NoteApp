package com.farzin.noteappmvp.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.ui.R
import com.example.local.Constants
import com.farzin.noteappmvp.data.models.NoteEntity
import com.farzin.noteappmvp.data.repository.main.MainRepository
import com.farzin.noteappmvp.databinding.ActivityMainBinding
import com.farzin.noteappmvp.ui.add.NoteFragment
import com.google.android.material.snackbar.Snackbar
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

    private var selectedItem = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            // set actionView (set Toolbar)
            setSupportActionBar(notesToolbar)

            //noteDetail
            addNoteBtn.setOnClickListener {
                NoteFragment().show(supportFragmentManager,NoteFragment().tag)
            }


            //load all notes
            mainPresenter.getAllNotes()

            /*notesAdapter.setOnClickListener { noteEntity->
                Toast.makeText(this@MainActivity, noteEntity.title, Toast.LENGTH_SHORT).show()
            }*/


            // show note details
            notesAdapter.setOnClickListener { noteEntity, state ->
                when(state){
                    Constants.DELETE->{
                        mainPresenter.deleteNote(noteEntity)
                    }
                    Constants.EDIT->{
                        val bundle = Bundle()
                        bundle.putInt(Constants.BUNDLE_ID,noteEntity.id)
                        val noteFragment = NoteFragment()
                        noteFragment.arguments = bundle
                        noteFragment.show(supportFragmentManager,NoteFragment().tag)
                    }
                }
            }

            //filter
            notesToolbar.setOnMenuItemClickListener {menuItem->
                when(menuItem.itemId){
                    com.farzin.noteappmvp.R.id.filter->{
                        showPriorityAlertDialogue()
                        return@setOnMenuItemClickListener true
                    }
                    else->{
                        return@setOnMenuItemClickListener false
                    }
                }
            }


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

    override fun showDeleteMessage() {
        Snackbar.make(binding.root,this.getString(R.string.delete_msg),Snackbar.LENGTH_SHORT).show()
    }

    private fun showPriorityAlertDialogue(){
        val builder = AlertDialog.Builder(this@MainActivity)

        val priorities = arrayOf(Constants.ALL,Constants.HIGH,Constants.MEDIUM,Constants.LOW)

        builder.setSingleChoiceItems(priorities,selectedItem){dialog,item->
            if (item == 0){
                mainPresenter.getAllNotes()
            }else{
                mainPresenter.getFilteredNotes(priorities[item])
            }
            selectedItem = item
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(com.farzin.noteappmvp.R.menu.menu_toolbar,menu)

        val search = menu.findItem(com.farzin.noteappmvp.R.id.search)

        val searchView = search.actionView as androidx.appcompat.widget.SearchView
        searchView.queryHint = this@MainActivity.getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                mainPresenter.searchNotes(newText)
                return true
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onStop() {
        super.onStop()
        mainPresenter.onStop()
    }
}