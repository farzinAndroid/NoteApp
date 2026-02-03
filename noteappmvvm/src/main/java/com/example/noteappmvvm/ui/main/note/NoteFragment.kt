package com.example.noteappmvvm.ui.main.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.example.local.Constants
import com.example.noteappmvvm.data.model.NoteEntity
import com.example.noteappmvvm.utils.getIndexFromList
import com.example.noteappmvvm.utils.setupSpinnerListWithAdapter
import com.example.noteappmvvm.viewmodel.NoteViewmodel
import com.example.ui.databinding.FragmentNoteBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.getValue


@AndroidEntryPoint
class NoteFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding


    //viewmodel
    private val viewModel by viewModels<NoteViewmodel>()

    private var category = ""
    private var priority = ""

    @Inject
    lateinit var noteEntity: NoteEntity

    private var noteId = -1
    private var type = ""

    private var categories = mutableListOf<String>()
    private var priorities = mutableListOf<String>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNoteBinding.inflate(layoutInflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteId = arguments?.getInt(Constants.BUNDLE_ID) ?: 0
        binding?.apply {

            //close
            closeImg.setOnClickListener {
                this@NoteFragment.dismiss()
            }

            //Spinners
            viewModel.createCategoriesList()
            viewModel.categoriesList.observe(viewLifecycleOwner) { categoryList ->
                categories.addAll(categoryList)
                categoriesSpinner.setupSpinnerListWithAdapter(categoryList) {
                    category = it
                }

            }

            viewModel.createPrioritiesList()
            viewModel.prioritiesList.observe(viewLifecycleOwner) { prioritiesList ->
                priorities.addAll(prioritiesList)
                prioritySpinner.setupSpinnerListWithAdapter(prioritiesList) {
                    priority = it
                }

            }

            //getExisting note
            if (noteId != 0){
                viewModel.getNote(noteId)
                viewModel.note.observe(this@NoteFragment){
                    it?.data.let { thisNote->
                        titleEdt.setText(thisNote?.title)
                        descEdt.setText(thisNote?.desc)
                        categoriesSpinner.setSelection(categories.getIndexFromList(thisNote?.category!!))
                        prioritySpinner.setSelection(priorities.getIndexFromList(thisNote.priority))
                    }
                }
            }

            //save update note
            saveNoteBtn.setOnClickListener {
                val title = titleEdt.text.toString()
                val description = descEdt.text.toString()

                noteEntity.id = noteId
                noteEntity.title = title
                noteEntity.desc = description
                noteEntity.category = category
                noteEntity.priority = priority


                if (title.isNotEmpty() && description.isNotEmpty()) {
                    if (noteId == 0){
                        viewModel.saveUpdateNote(noteEntity, true)
                    }else{
                        viewModel.saveUpdateNote(noteEntity, false)
                    }
                }
                this@NoteFragment.dismiss()
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}