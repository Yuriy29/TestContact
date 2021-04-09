package com.example.testcontactlistapp.di.addContact

import androidx.lifecycle.ViewModel
import com.example.testcontactlistapp.di.ViewModelKey
import com.example.testcontactlistapp.ui.createContact.CreateContactViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AddContactViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CreateContactViewModel::class)
    abstract fun bindAddContactViewModel(addContactViewModel: CreateContactViewModel): ViewModel

}