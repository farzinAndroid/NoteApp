package com.farzin.noteappmvp.utils.di

import androidx.fragment.app.Fragment
import com.farzin.noteappmvp.ui.add.AddNoteContracts
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
class ContractsViewModuleFR {

    @Provides
    fun provideAddNoteContractView(fragment:Fragment) : AddNoteContracts.View{
        return fragment as AddNoteContracts.View
    }

}