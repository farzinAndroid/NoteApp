package com.example.noteappmvi.viewmodel.note_detail

import com.example.noteappmvi.data.model.NoteEntity

sealed class DetailIntent {

    data object CreateSpinnerData : DetailIntent()
    data class SaveUpdateNote(val noteEntity: NoteEntity) : DetailIntent()
    data class GetNoteDetail(val id: Int) : DetailIntent()
}