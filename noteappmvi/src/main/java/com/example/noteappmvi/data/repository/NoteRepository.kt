package com.example.noteappmvi.data.repository

import com.example.noteappmvi.data.database.NoteDao
import com.example.noteappmvi.data.model.NoteEntity
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val dao: NoteDao
) {

    suspend fun saveNote(note: NoteEntity) = dao.saveNote(note)

    suspend fun updateNote(note: NoteEntity) = dao.updateNote(note)
    fun getNote(noteId: Int) = dao.getNote(noteId)


}