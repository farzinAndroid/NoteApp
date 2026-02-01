package com.example.noteappmvvm.utils.di

import android.content.Context
import androidx.room.Room
import com.example.local.Constants
import com.example.noteappmvvm.data.database.NoteDatabase
import com.example.noteappmvvm.data.model.NoteEntity
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