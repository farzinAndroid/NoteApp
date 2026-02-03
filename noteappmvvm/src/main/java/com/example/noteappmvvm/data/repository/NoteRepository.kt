package com.example.noteappmvvm.data.repository

import com.example.noteappmvvm.data.database.NoteDao
import com.example.noteappmvvm.data.model.NoteEntity
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val dao: NoteDao
) {

    suspend fun saveNote(note: NoteEntity) = dao.saveNote(note)

    suspend fun updateNote(note: NoteEntity) = dao.updateNote(note)
    fun getNote(noteId: Int) = dao.getNote(noteId)


}