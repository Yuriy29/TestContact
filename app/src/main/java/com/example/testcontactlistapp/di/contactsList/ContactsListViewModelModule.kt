package com.example.testcontactlistapp.di.contactsList

import androidx.lifecycle.ViewModel
import com.example.testcontactlistapp.di.ViewModelKey
import com.example.testcontactlistapp.ui.contactsList.ContactsListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ContactsListViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ContactsListViewModel::class)
    abstract fun bindContactsListViewModel(contactsListViewModel: ContactsListViewModel): ViewModel

}