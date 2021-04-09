package com.example.testcontactlistapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel()  {
    protected val compositeDisposable = CompositeDisposable()
    val _error = MutableLiveData<Throwable>()
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}