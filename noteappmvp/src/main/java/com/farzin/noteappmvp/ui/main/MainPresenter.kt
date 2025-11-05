package com.farzin.noteappmvp.ui.main

import com.farzin.noteappmvp.base.BasePresenterImpl
import com.farzin.noteappmvp.data.models.NoteEntity
import com.farzin.noteappmvp.data.repository.main.MainRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MainPresenter @Inject constructor(
    private val repository: MainRepository,
    private val view: MainContracts.View,
) : BasePresenterImpl(), MainContracts.Presenter {


    override fun getAllNotes() {
        disposable = repository.getAllNotes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { notesList ->
                if (notesList.isNotEmpty()) {
                    view.showAllNotes(notesList)
                } else {
                    view.showEmptyList()
                }
            }
    }

    override fun deleteNote(noteEntity: NoteEntity) {
        disposable = repository.deleteNote(noteEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.showDeleteMessage()
            }
    }

    override fun getFilteredNotes(priority: String) {
        disposable = repository.getFilteredNotes(priority)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {notesList ->
                if (notesList.isNotEmpty()) {
                    view.showAllNotes(notesList)
                } else {
                    view.showEmptyList()
                }
            }
    }

    override fun searchNotes(search: String) {
        disposable = repository.searchNote(search)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {notesList ->
                if (notesList.isNotEmpty()) {
                    view.showAllNotes(notesList)
                } else {
                    view.showEmptyList()
                }
            }
    }


}