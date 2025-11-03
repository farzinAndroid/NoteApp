package com.farzin.noteappmvp.ui.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.farzin.noteappmvp.R
import com.farzin.noteappmvp.databinding.FragmentNoteBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NoteFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNoteBinding

    //Other
    private lateinit var categoriesList : Array<String>
    private var category = ""
    private lateinit var priorityList : Array<String>
    private var priority = ""

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

        // init views
        binding.apply {
            //close
            closeImg.setOnClickListener {
                this@NoteFragment.dismiss()
            }

            // init spinners
            createCategoriesSpinner()
            createPrioritySpinner()
        }
    }


    private fun createCategoriesSpinner(){
        categoriesList = arrayOf("Home","Health","Education","Work")
        val adapter =ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item,categoriesList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.categoriesSpinner.adapter = adapter
        binding.categoriesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                category = categoriesList[position]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }
    }

    private fun createPrioritySpinner(){
        priorityList = arrayOf("High","Medium","Low")
        val adapter =ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item,priorityList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.prioritySpinner.adapter = adapter
        binding.prioritySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                priority = priorityList[position]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }
    }

}