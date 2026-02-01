package com.farzin.noteappmvp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.local.Constants

@Entity(tableName = Constants.TABLE_NAME)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Int = -0,
    var title:String = "",
    var category:String = "",
    var desc:String = "",
    var priority:String = "",
)
