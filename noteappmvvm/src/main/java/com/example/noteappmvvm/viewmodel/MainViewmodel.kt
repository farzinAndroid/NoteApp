package com.example.noteappmvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteappmvvm.data.model.NoteEntity
import com.example.noteappmvvm.data.repository.MainRepository
import com.example.noteappmvvm.utils.DataStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewmodel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    val allNotes = MutableLiveData<DataStatus<List<NoteEntity>>>()


    fun getAllNotes() = viewModelScope.launch(Dispatchers.IO) {
        mainRepository.getAllNotes().collect {
            allNotes.postValue(DataStatus.success(it, it.isEmpty()))
        }
    }



    fun getFilteredNotes(priority: String) = viewModelScope.launch(Dispatchers.IO) {
        mainRepository.getFilteredNotes(priority).collect {
            allNotes.postValue(DataStatus.success(it, it.isEmpty()))
        }
    }

    fun getSearchedNotes(search: String) = viewModelScope.launch(Dispatchers.IO) {
        mainRepository.searchNotes(search).collect {
            allNotes.postValue(DataStatus.success(it, it.isEmpty()))
        }
    }

    fun deleteNote(noteEntity: NoteEntity) = viewModelScope.launch(Dispatchers.IO) {
        mainRepository.deleteNote(noteEntity)
    }

}