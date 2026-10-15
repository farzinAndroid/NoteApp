package com.example.noteappmvi.viewmodel.main

import com.example.noteappmvi.data.model.NoteEntity

sealed class MainIntent {

    data object LoadAllNotes : MainIntent()
    data class SearchNote(val search: String) : MainIntent()
    data class FilterNote(val filter: String) : MainIntent()
    data class DeleteNote(val noteEntity: NoteEntity) : MainIntent()
    data class GoToDetail(val id: Int) : MainIntent()

}