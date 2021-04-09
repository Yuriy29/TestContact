package com.example.testcontactlistapp.data

import com.example.testcontactlistapp.data.local.ContactDao
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactRepository @Inject constructor(private val contactDao: ContactDao) {

    fun getAllContacts() : Single<List<ContactEntity>?> = contactDao.getAllContacts()

    fun insertContact(contact: ContactEntity):Completable {
        return contactDao.insertContact(contact)
    }

    fun deleteContact(contact: ContactEntity?) = contactDao.deleteContact(contact)

    fun updateContact(contact: ContactEntity) = contactDao.updateContact(contact)

    fun findContactByName(query: String): Single<List<ContactEntity>> = contactDao.findContactByName(query)

    fun getContactById(id: Int): Single<ContactEntity?> = contactDao.getContactById(id)
}
