package com.farzin.noteappmvp.utils.di

import android.app.Activity
import com.farzin.noteappmvp.ui.main.MainContracts
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class ContractsViewModuleAC {

    @Provides
    fun provideMainContractView(activity: Activity) : MainContracts.View{
        return activity as MainContracts.View
    }

}