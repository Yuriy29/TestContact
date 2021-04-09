package com.example.testcontactlistapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.testcontactlistapp.data.ContactEntity

@Database(entities = arrayOf(ContactEntity::class), version = 1, exportSchema = false)
@TypeConverters(RoomDataConverter::class)
abstract class ContactRoomDatabase : RoomDatabase(){
    companion object {
        const val DATABASE_NAME = "contacts_db"
    }

    abstract fun contactDao(): ContactDao
}

