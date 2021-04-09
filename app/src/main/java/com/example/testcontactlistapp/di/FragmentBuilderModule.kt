package com.example.testcontactlistapp.di

import com.example.testcontactlistapp.di.addContact.AddContactViewModelModule
import com.example.testcontactlistapp.di.contactDetails.ContactDetailsViewModelModule
import com.example.testcontactlistapp.di.contactsList.ContactsListViewModelModule
import com.example.testcontactlistapp.ui.createContact.CreateContactFragment
import com.example.testcontactlistapp.ui.detailsContact.ContactDetailsFragment
import com.example.testcontactlistapp.ui.contactsList.ContactsListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector(modules = [ContactsListViewModelModule::class])
    abstract fun contributeContactsFragment() : ContactsListFragment

    @ContributesAndroidInjector(modules = [AddContactViewModelModule::class])
    abstract fun contributeAddContactFragment() : CreateContactFragment

    @ContributesAndroidInjector(modules = [ContactDetailsViewModelModule::class])
    abstract fun contributeContactDetailsFragment() : ContactDetailsFragment
}