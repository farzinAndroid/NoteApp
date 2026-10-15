package com.example.noteappmvi.viewmodel.note_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.local.Constants
import com.example.noteappmvi.data.model.NoteEntity
import com.example.noteappmvi.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewmodel @Inject constructor(private val repository: NoteRepository) : ViewModel() {


    val noteDetailIntentChannel = Channel<DetailIntent>()
    private val _state = MutableStateFlow<DetailState>(DetailState.Idle)
    val state = _state

    init {
        handleIntents()
    }


    private fun handleIntents() = viewModelScope.launch {
        noteDetailIntentChannel.consumeAsFlow().collectLatest {
            when (it) {
                is DetailIntent.SaveUpdateNote -> {
                    saveUpdateNote(it.noteEntity)
                }
                DetailIntent.CreateSpinnerData -> fetchSpinnersList()
                is DetailIntent.GetNoteDetail -> fetchNoteDetail(it.id)
            }
        }
    }

    private fun fetchNoteDetail(id: Int) = viewModelScope.launch {
        repository.getNote(id).collectLatest {
            _state.emit(DetailState.ShowSelectedNoteDetail(it))
        }
    }

    private fun saveUpdateNote(noteEntity: NoteEntity) = viewModelScope.launch {
        _state.value = try {
            if (noteEntity.id == 0) {
                repository.saveNote(noteEntity)
            } else {
                repository.updateNote(noteEntity)
            }
            DetailState.CloseFragment
        } catch (e: Exception) {
            DetailState.Error(e.message.toString())
        }
    }

    private fun fetchSpinnersList() {
        val categories = mutableListOf<String>(
            Constants.WORK,
            Constants.EDUCATION,
            Constants.HOME,
            Constants.HEALTH
        )

        val priorities = mutableListOf<String>(
            Constants.HIGH,
            Constants.MEDIUM,
            Constants.LOW,
        )

        _state.value = DetailState.ShowSpinnerData(categories,priorities)
    }

}