package com.example.testcontactlistapp.ui.contactsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testcontactlistapp.data.ContactEntity
import com.example.testcontactlistapp.data.ContactRepository
import com.example.testcontactlistapp.ui.BaseViewModel
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ContactsListViewModel @Inject constructor(private val contactRepository: ContactRepository) :
    BaseViewModel() {

    private val _listContact = MutableLiveData<List<ContactEntity>>()
    private val _listFindContact = MutableLiveData<List<ContactEntity>>()

    fun onUpdateContactList(): LiveData<List<ContactEntity>> = _listContact
    fun onUpdateContactFindList(): LiveData<List<ContactEntity>> = _listFindContact

    fun getAllContacts() {
        contactRepository.getAllContacts()
            .subscribeOn(Schedulers.io())
            .subscribe({
                _listContact.postValue(it)
            },
                {
                    _error.postValue(it)
                }
            )
            .let(compositeDisposable::add)
    }

    fun findContactByName(query: String) {
        contactRepository.findContactByName(query)
            .subscribeOn(Schedulers.io())
            .subscribe({
                _listFindContact.postValue(it)
            },
                {
                    _error.postValue(it)
                }
            )
            .let(compositeDisposable::add)
    }
}