package com.farzin.noteappmvp.ui.add

import com.farzin.noteappmvp.base.BasePresenter
import com.farzin.noteappmvp.data.models.NoteEntity

interface AddNoteContracts {

    interface View{
        fun closeBottomSheetFragment()
    }

    interface Presenter : BasePresenter{
        fun saveNote(noteEntity: NoteEntity)
    }

}