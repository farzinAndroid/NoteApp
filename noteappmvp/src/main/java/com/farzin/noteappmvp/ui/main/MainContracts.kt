package com.farzin.noteappmvp.ui.main

import com.farzin.noteappmvp.base.BasePresenter
import com.farzin.noteappmvp.data.models.NoteEntity

interface MainContracts {

    interface View{
        fun showAllNotes(notesList : List<NoteEntity>)
        fun showEmptyList()
        fun showDeleteMessage()
    }

    interface Presenter : BasePresenter{
        fun getAllNotes()
        fun deleteNote(noteEntity: NoteEntity)
    }

}