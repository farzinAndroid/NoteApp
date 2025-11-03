package com.farzin.noteappmvp.data.repository

import com.farzin.noteappmvp.data.database.NoteDao
import com.farzin.noteappmvp.data.models.NoteEntity
import javax.inject.Inject

class AddRepository @Inject constructor(private val dao:NoteDao) {

    fun saveNote(noteEntity: NoteEntity) = dao.saveNote(noteEntity)

}