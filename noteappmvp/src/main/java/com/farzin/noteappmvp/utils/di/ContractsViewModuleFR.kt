package com.farzin.noteappmvp.utils.di

import androidx.fragment.app.Fragment
import com.farzin.noteappmvp.ui.add.NoteContracts
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
class ContractsViewModuleFR {

    @Provides
    fun provideAddNoteContractView(fragment:Fragment) : NoteContracts.View{
        return fragment as NoteContracts.View
    }

}