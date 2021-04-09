package com.example.testcontactlistapp.ui.detailsContact

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testcontactlistapp.data.ContactEntity
import com.example.testcontactlistapp.data.ContactRepository
import com.example.testcontactlistapp.ui.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ContactDetailsViewModel @Inject constructor(private val contactRepository: ContactRepository) :
    BaseViewModel() {
    private val _contact = MutableLiveData<ContactEntity>()
    fun onUpdateContact(): LiveData<ContactEntity> = _contact

    fun getContactByInt(contactId: Int) {
        contactRepository.getContactById(contactId)
            .subscribeOn(Schedulers.io())
            .subscribe({
                _contact.postValue(it)
            },
                {
                    _error.postValue(it)
                }
            )
            .let(compositeDisposable::add)
    }

    private fun deleteRepositoryContact(contact: ContactEntity?) {
        contactRepository.deleteContact(contact)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .let(compositeDisposable::add)
    }

    fun deleteContact(contact: ContactEntity?) {
        deleteRepositoryContact(contact)
    }
}