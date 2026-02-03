package com.example.noteappmvvm.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.noteappmvvm.data.model.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNote(noteEntity: NoteEntity)

    @Delete
    suspend fun deleteNote(noteEntity: NoteEntity)

    @Update
    suspend fun updateNote(noteEntity: NoteEntity)

    @Query("select * from note_table")
    fun getAllNotes() : Flow<MutableList<NoteEntity>>

    @Query("select * from note_table where id == :id")
    fun getNote(id:Int) : Flow<NoteEntity>

    @Query("select * from note_table where priority == :priority")
    fun getFilteredNotes(priority: String) : Flow<MutableList<NoteEntity>>

    @Query("select * from note_table where title like '%' || :search || '%' ")
    fun searchNote(search:String) :Flow<MutableList<NoteEntity>>

}