package com.farzin.noteappmvp.data.repository.main

import com.farzin.noteappmvp.data.database.NoteDao
import com.farzin.noteappmvp.data.models.NoteEntity
import javax.inject.Inject

class MainRepository @Inject constructor(private val dao:NoteDao) {

    fun getAllNotes() = dao.getAllNotes()
    fun deleteNote(noteEntity: NoteEntity) = dao.deleteNote(noteEntity)
    fun getFilteredNotes(priority: String) = dao.getFilteredNotes(priority)
    fun searchNote(search:String) = dao.searchNote(search)

}