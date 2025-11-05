package com.farzin.noteappmvp.data.repository.note

import com.farzin.noteappmvp.data.database.NoteDao
import com.farzin.noteappmvp.data.models.NoteEntity
import javax.inject.Inject

class NoteRepository @Inject constructor(private val dao:NoteDao) {

    fun saveNote(noteEntity: NoteEntity) = dao.saveNote(noteEntity)
    fun getNote(noteId:Int) = dao.getNote(noteId)
    fun updateNote(noteEntity: NoteEntity) = dao.updateNote(noteEntity)

}