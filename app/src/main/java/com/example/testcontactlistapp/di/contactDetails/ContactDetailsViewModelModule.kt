package com.example.testcontactlistapp.di.contactDetails

import androidx.lifecycle.ViewModel
import com.example.testcontactlistapp.di.ViewModelKey
import com.example.testcontactlistapp.ui.detailsContact.ContactDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ContactDetailsViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ContactDetailsViewModel::class)
    abstract fun bindContactDetailsViewModel(contactDetailsViewModel: ContactDetailsViewModel): ViewModel
}