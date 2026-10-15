package com.example.noteappmvi.ui.main.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.local.Constants
import com.example.noteappmvi.data.model.NoteEntity
import com.example.noteappmvi.utils.getIndexFromList
import com.example.noteappmvi.utils.setupSpinnerListWithAdapter
import com.example.noteappmvi.viewmodel.note_detail.DetailIntent
import com.example.noteappmvi.viewmodel.note_detail.DetailState
import com.example.noteappmvi.viewmodel.note_detail.NoteViewmodel
import com.example.ui.databinding.FragmentNoteBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class NoteFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding

    @Inject
    lateinit var noteEntity: NoteEntity

    private val viewmodel : NoteViewmodel by viewModels<NoteViewmodel>()
    private var category = ""
    private var priority = ""
    private var categoryList = mutableListOf<String>()
    private var priorityList = mutableListOf<String>()


    private var noteId = -1


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

        //bundle
        noteId = arguments?.getInt(Constants.BUNDLE_ID) ?: 0


        binding?.apply {
            //close
            closeImg.setOnClickListener { dismiss() }

            //save
            saveNoteBtn.setOnClickListener {
                val title = titleEdt.text.toString()
                val desc = descEdt.text.toString()

                noteEntity.id = noteId
                noteEntity.title = title
                noteEntity.desc = desc
                noteEntity.priority = priority
                noteEntity.category = category

                lifecycleScope.launch {
                    viewmodel.noteDetailIntentChannel.send(DetailIntent.SaveUpdateNote(noteEntity))
                }
            }

            //data
            lifecycleScope.launch {
                //send
                viewmodel.noteDetailIntentChannel.send(DetailIntent.CreateSpinnerData)
                if (noteId != 0) {
                    viewmodel.noteDetailIntentChannel.send(DetailIntent.GetNoteDetail(noteId))
                }
                //getdata
                viewmodel.state.collectLatest { state ->
                    when (state) {
                        is DetailState.Error -> {
                            Toast.makeText(requireContext(), state.error, Toast.LENGTH_SHORT).show()
                        }

                        DetailState.Idle -> {}
                        is DetailState.CloseFragment -> {
                            dismiss()
                        }

                        is DetailState.ShowSpinnerData -> {
                            categoryList.addAll(state.categoriesList)
                            categoriesSpinner.setupSpinnerListWithAdapter(state.categoriesList) {
                                category = it
                            }

                            priorityList.addAll(state.prioritiesList)
                            prioritySpinner.setupSpinnerListWithAdapter(state.prioritiesList) {
                                priority = it
                            }
                        }

                        is DetailState.ShowSelectedNoteDetail -> {
                            titleEdt.setText(state.noteEntity.title)
                            descEdt.setText(state.noteEntity.desc)
                            categoriesSpinner.setSelection(categoryList.getIndexFromList(state.noteEntity.category))
                            prioritySpinner.setSelection(priorityList.getIndexFromList(state.noteEntity.priority))
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}