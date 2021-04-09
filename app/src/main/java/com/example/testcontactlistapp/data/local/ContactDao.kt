package com.example.testcontactlistapp.data.local

import androidx.room.*
import com.example.testcontactlistapp.data.ContactEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface ContactDao {

    @Query("SELECT * FROM ContactEntity ORDER BY firstName ASC")
    fun getAllContacts(): Single<List<ContactEntity>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContact(contact: ContactEntity): Completable

    @Delete
    fun deleteContact(contact: ContactEntity?): Completable

    @Update
    fun updateContact(contact: ContactEntity): Completable

    @Query("SELECT * from ContactEntity WHERE firstName OR lastName LIKE '%' || :query || '%'")
    fun findContactByName(query: String): Single<List<ContactEntity>>

    @Query("SELECT * FROM ContactEntity WHERE id = :contactId")
    fun getContactById(contactId: Int): Single<ContactEntity?>
}