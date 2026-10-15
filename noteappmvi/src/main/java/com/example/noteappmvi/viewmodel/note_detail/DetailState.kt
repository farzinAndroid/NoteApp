package com.example.noteappmvi.viewmodel.note_detail

import com.example.noteappmvi.data.model.NoteEntity

sealed class DetailState {

    data class ShowSpinnerData(
        val categoriesList: MutableList<String>,
        val prioritiesList: MutableList<String>
    ) : DetailState()

    data class Error(val error: String) : DetailState()
    data object CloseFragment : DetailState()
    data object Idle : DetailState()

    data class ShowSelectedNoteDetail(val noteEntity: NoteEntity) : DetailState()
}