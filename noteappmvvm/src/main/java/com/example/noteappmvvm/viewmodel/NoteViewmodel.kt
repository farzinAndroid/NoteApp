package com.example.noteappmvvm.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.local.Constants
import com.example.noteappmvvm.data.model.NoteEntity
import com.example.noteappmvvm.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewmodel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {


    //Notes
    fun saveUpdateNote(noteEntity: NoteEntity,isSave: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        if (isSave){
            repository.saveNote(noteEntity)
        }else{
            repository.updateNote(noteEntity)
        }
    }













    //Spinners
    val categoriesList = MutableLiveData<MutableList<String>>()
    val prioritiesList = MutableLiveData<MutableList<String>>()

    fun createCategoriesList() = viewModelScope.launch(Dispatchers.IO) {
        val categories = mutableListOf<String>(
            Constants.WORK,
            Constants.EDUCATION,
            Constants.HOME,
            Constants.HEALTH
        )
        categoriesList.postValue(categories)
    }


    fun createPrioritiesList() = viewModelScope.launch(Dispatchers.IO) {
        val priorities = mutableListOf<String>(
            Constants.HIGH,
            Constants.MEDIUM,
            Constants.LOW,
            Constants.ALL,
        )
        prioritiesList.postValue(priorities)
    }


}