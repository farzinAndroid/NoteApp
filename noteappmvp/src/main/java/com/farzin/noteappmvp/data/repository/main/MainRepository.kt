package com.farzin.noteappmvp.data.repository.main

import com.farzin.noteappmvp.data.database.NoteDao
import javax.inject.Inject

class MainRepository @Inject constructor(private val dao:NoteDao) {

    fun getAllNotes() = dao.getAllNotes()

}