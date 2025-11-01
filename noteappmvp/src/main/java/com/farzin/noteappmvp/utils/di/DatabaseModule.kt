package com.farzin.noteappmvp.utils.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.farzin.noteappmvp.data.database.NoteDatabase
import com.farzin.noteappmvp.data.models.NoteEntity
import com.farzin.noteappmvp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {


    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context:Context
    ) = Room.databaseBuilder(
        context = context,
        name = Constants.DB_NAME,
        klass = NoteDatabase::class.java
    )
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration(true)
        .build()

    @Provides
    @Singleton
    fun provideDao(
        db:NoteDatabase
    ) = db.noteDao()

    @Provides
    @Singleton
    fun provideNoteEntity() = NoteEntity()

}