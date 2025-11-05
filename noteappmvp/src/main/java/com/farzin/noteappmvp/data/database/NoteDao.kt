package com.farzin.noteappmvp.data.database

import android.renderscript.RenderScript.Priority
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.farzin.noteappmvp.data.models.NoteEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveNote(noteEntity: NoteEntity) : Completable

    @Delete
    fun deleteNote(noteEntity: NoteEntity) : Completable

    @Update
    fun updateNote(noteEntity: NoteEntity) : Completable

    @Query("select * from note_table")
    fun getAllNotes() : Observable<List<NoteEntity>>

    @Query("select * from note_table where id == :id")
    fun getNote(id:Int) : Observable<NoteEntity>

    @Query("select * from note_table where priority == :priority")
    fun getFilteredNotes(priority: String) : Observable<List<NoteEntity>>

}