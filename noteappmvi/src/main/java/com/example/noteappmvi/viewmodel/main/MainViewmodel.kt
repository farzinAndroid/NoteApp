package com.example.noteappmvi.viewmodel.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteappmvi.data.model.NoteEntity
import com.example.noteappmvi.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewmodel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {


    private val _state = MutableStateFlow<MainState>(MainState.ShowEmptyScreen)
    val state = _state


    fun handleIntent(mainIntent: MainIntent) {
        when (mainIntent) {
            MainIntent.LoadAllNotes -> fetchAllNotes()
            is MainIntent.DeleteNote -> deleteNote(mainIntent.noteEntity)
            is MainIntent.FilterNote -> filterNotes(mainIntent.filter)
            is MainIntent.SearchNote -> searchNotes(mainIntent.search)
            is MainIntent.GoToDetail -> goToDetailFragment(mainIntent.id)
        }
    }

    private fun goToDetailFragment(id: Int) {
        _state.value = MainState.OpenDetail(id)
    }

    private fun searchNotes(search: String) = viewModelScope.launch {
        mainRepository.searchNotes(search).collectLatest {
            _state.emit(
                if (it.isNotEmpty()) {
                    MainState.ShowNotes(it)
                } else {
                    MainState.ShowEmptyScreen
                }
            )
        }
    }

    private fun filterNotes(filter: String) = viewModelScope.launch {
        mainRepository.getFilteredNotes(filter).collectLatest {
            _state.emit(
                if (it.isNotEmpty()){
                    MainState.ShowNotes(it)
                }else{
                    MainState.ShowEmptyScreen
                }
            )
        }
    }

    private fun deleteNote(noteEntity: NoteEntity) = viewModelScope.launch {
        _state.emit(MainState.DeleteNote(mainRepository.deleteNote(noteEntity)))
    }

    private fun fetchAllNotes() = viewModelScope.launch {
        mainRepository.getAllNotes().collectLatest {
            if (it.isNotEmpty()) {
                _state.emit(MainState.ShowNotes(it))
            } else {
                _state.emit(MainState.ShowEmptyScreen)
            }
        }
    }
}