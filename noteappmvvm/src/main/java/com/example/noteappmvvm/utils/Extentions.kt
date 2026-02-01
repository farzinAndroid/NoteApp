package com.example.noteappmvvm.utils

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.ui.R


fun Spinner.setupSpinnerListWithAdapter(
    list:List<String>,
    callBack:(String) -> Unit
){
    val adapter = ArrayAdapter(context,android.R.layout.simple_spinner_item,list)
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    this.adapter = adapter
    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            callBack(list[position])
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
        }


    }
}