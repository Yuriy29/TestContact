package com.example.testcontactlistapp.ui.createContact

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testcontactlistapp.data.ContactEntity
import com.example.testcontactlistapp.data.ContactRepository
import com.example.testcontactlistapp.ui.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CreateContactViewModel @Inject constructor(private val contactRepository: ContactRepository) :
    BaseViewModel() {

    var firstName = ""
    var lastName = ""
    var phoneNumbers = mutableMapOf<Int, String>()
    var emails = mutableMapOf<Int, String>()
    var keyDataList = 0

    private val _contact = MutableLiveData<ContactEntity>()
    fun onUpdateContact(): LiveData<ContactEntity> = _contact

    private fun updateRepositoryContact(contact: ContactEntity) {
        contactRepository.updateContact(contact)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .let(compositeDisposable::add)
    }

    fun updateContact(contact: ContactEntity) {
        updateRepositoryContact(contact)
    }

    fun saveContact(contact: ContactEntity) {
        insertContact(contact)
    }

    private fun insertContact(contact: ContactEntity) {
        contactRepository.insertContact(contact)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .let(compositeDisposable::add)
    }

    fun getContactById(contactId: Int) {
        contactRepository.getContactById(contactId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { contact ->
                contact?.number?.forEach {
                    phoneNumbers.put(keyDataList, it)
                    keyDataList++
                }
                contact?.email?.forEach {
                    emails.put(keyDataList, it)
                    keyDataList++
                }
                _contact.value = contact
            }
            .let(compositeDisposable::add)
    }
}