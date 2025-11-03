package com.farzin.noteappmvp.ui.add

import com.farzin.noteappmvp.base.BasePresenterImpl
import com.farzin.noteappmvp.data.models.NoteEntity
import com.farzin.noteappmvp.data.repository.AddRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class AddNotePresenter @Inject constructor(private val repository: AddRepository, private val view: AddNoteContracts.View)
    : BasePresenterImpl() , AddNoteContracts.Presenter{

    override fun saveNote(noteEntity: NoteEntity) {
        disposable = repository.saveNote(noteEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.closeBottomSheetFragment()
            }
    }




}