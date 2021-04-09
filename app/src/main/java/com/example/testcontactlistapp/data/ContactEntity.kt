package com.example.testcontactlistapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class ContactEntity(
    var firstName: String?,
    var lastName: String?,
    var number: List<String>?,
    var email: List<String>?,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)