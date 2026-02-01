package com.farzin.noteappmvp.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.example.local.Constants
import android.widget.ArrayAdapter
import com.farzin.noteappmvp.data.models.NoteEntity
import com.farzin.noteappmvp.data.repository.note.NoteRepository
import com.farzin.noteappmvp.databinding.FragmentNoteBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NoteFragment : BottomSheetDialogFragment(), NoteContracts.View {

    private lateinit var binding: FragmentNoteBinding

    @Inject
    lateinit var noteEntity: NoteEntity

    @Inject
    lateinit var repository: NoteRepository

    @Inject
    lateinit var notePresenter: NotePresenter

//    private val notePresenter by lazy { NotePresenter(repository,this) }


    //Other
    private lateinit var categoriesList: Array<String>
    private var category = ""
    private lateinit var priorityList: Array<String>
    private var priority = ""
    private var noteId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNoteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteId = arguments?.getInt(Constants.BUNDLE_ID, 0) ?: 0

        // init views
        binding.apply {
            //close
            closeImg.setOnClickListener {
                this@NoteFragment.dismiss()
            }

            // init spinners
            createCategoriesSpinner()
            createPrioritySpinner()


            //Save
            saveNoteBtn.setOnClickListener {
                val title = titleEdt.text.toString()
                val desc = descEdt.text.toString()
                //entity
//                noteEntity.id = 0
                noteEntity.id = noteId
                noteEntity.title = title
                noteEntity.desc = desc
                noteEntity.priority = priority
                noteEntity.category = category
                //save and update
                if (noteId == 0) {
                    notePresenter.saveNote(noteEntity)
                } else {
                    notePresenter.updateNote(noteEntity)
                }

            }

            //show note detail
            if (noteId != 0) {
                notePresenter.getNote(noteId)
            }
        }
    }


    private fun createCategoriesSpinner() {
        categoriesList =
            arrayOf(Constants.HEALTH, Constants.HOME, Constants.WORK, Constants.EDUCATION)
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categoriesList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.categoriesSpinner.adapter = adapter
        binding.categoriesSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    category = categoriesList[position]
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

            }
    }

    private fun createPrioritySpinner() {
        priorityList = arrayOf(Constants.HIGH, Constants.MEDIUM, Constants.LOW)
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, priorityList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.prioritySpinner.adapter = adapter
        binding.prioritySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    priority = priorityList[position]
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

            }
    }

    override fun closeBottomSheetFragment() {
        this.dismiss()
    }

    override fun showNoteDetails(noteEntity: NoteEntity) {
        binding.apply {
            if (this@NoteFragment.isAdded) {
                requireActivity().runOnUiThread {
                    titleEdt.setText(noteEntity.title)
                    descEdt.setText(noteEntity.desc)
                    categoriesSpinner.setSelection(
                        getSpinnerItemsIndex(
                            categoriesList,
                            noteEntity.category
                        )
                    )
                    prioritySpinner.setSelection(
                        getSpinnerItemsIndex(
                            priorityList,
                            noteEntity.priority
                        )
                    )
                }
            }
        }
    }

    fun getSpinnerItemsIndex(list: Array<String>, item: String): Int {
        var index = 0
        for (i in list.indices) {
            if (list[i] == item) {
                index = i
                break
            }
        }
        return index
    }

    override fun onStop() {
        super.onStop()
        notePresenter.onStop()
    }

}