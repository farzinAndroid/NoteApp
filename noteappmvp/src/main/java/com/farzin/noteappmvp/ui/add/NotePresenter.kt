package com.farzin.noteappmvp.ui.add

import com.farzin.noteappmvp.base.BasePresenterImpl
import com.farzin.noteappmvp.data.models.NoteEntity
import com.farzin.noteappmvp.data.repository.note.NoteRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class NotePresenter @Inject constructor(
    private val repository: NoteRepository,
    private val view: NoteContracts.View,
) : BasePresenterImpl(), NoteContracts.Presenter {

    override fun saveNote(noteEntity: NoteEntity) {
        disposable = repository.saveNote(noteEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.closeBottomSheetFragment()
            }
    }

    override fun getNote(noteId: Int) {
        disposable = repository.getNote(noteId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.showNoteDetails(it)
            }
    }

    override fun updateNote(noteEntity: NoteEntity) {
        disposable = repository.updateNote(noteEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.closeBottomSheetFragment()
            }
    }


}