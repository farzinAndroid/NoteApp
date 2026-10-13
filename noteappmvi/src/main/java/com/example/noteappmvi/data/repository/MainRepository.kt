package com.example.noteappmvi.data.repository

import com.example.noteappmvi.data.database.NoteDao
import com.example.noteappmvi.data.model.NoteEntity
import javax.inject.Inject

class MainRepository @Inject constructor(private val dao: NoteDao) {

    fun getAllNotes() = dao.getAllNotes()
    fun searchNotes(str: String) = dao.searchNote(str)
    fun getFilteredNotes(priority: String) = dao.getFilteredNotes(priority)
    suspend fun deleteNote(noteEntity: NoteEntity) = dao.deleteNote(noteEntity)


}