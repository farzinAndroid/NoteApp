package com.farzin.noteappmvp.ui.add

import com.farzin.noteappmvp.base.BasePresenter
import com.farzin.noteappmvp.data.models.NoteEntity

interface NoteContracts {

    interface View{
        fun closeBottomSheetFragment()
        fun showNoteDetails(noteEntity: NoteEntity)
    }

    interface Presenter : BasePresenter{
        fun saveNote(noteEntity: NoteEntity)
        fun getNote(noteId:Int)
        fun updateNote(noteEntity: NoteEntity)
    }

}