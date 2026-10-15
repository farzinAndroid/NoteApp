package com.example.noteappmvi.viewmodel.main

import com.example.noteappmvi.data.model.NoteEntity
import com.example.noteappmvi.viewmodel.note_detail.DetailState

sealed class MainState {

    data class ShowNotes(val noteList: MutableList<NoteEntity>) : MainState()
    data object ShowEmptyScreen : MainState()
    data class DeleteNote(val deleteNote : Unit) : MainState()
    data class OpenDetail(val id: Int) : MainState()

}