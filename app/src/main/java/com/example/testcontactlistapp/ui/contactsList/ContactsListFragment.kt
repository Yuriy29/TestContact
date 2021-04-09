package com.example.testcontactlistapp.ui.contactsList

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController

import com.example.testcontactlistapp.MainActivity
import com.example.testcontactlistapp.R
import com.example.testcontactlistapp.databinding.FragmentContactsListBinding
import com.example.testcontactlistapp.di.ViewModelProviderFactory
import com.example.testcontactlistapp.util.OPTIONS
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ContactsListFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    private lateinit var viewModel: ContactsListViewModel
    lateinit var binding : FragmentContactsListBinding
    lateinit var adapter :ContactsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentContactsListBinding.inflate(inflater, container, false)

        viewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(ContactsListViewModel::class.java)

        initAdapter()
        initUI()

        viewModel.getAllContacts()
        return binding.root
    }

    private fun initAdapter(): ContactsAdapter {
        adapter = ContactsAdapter(context!!, ContactsClickListener {
            val id = it.id
            this.findNavController().navigate(
                ContactsListFragmentDirections
                    .actionContactsListFragmentToContactDetailsFragment(id)
            )
        })

        binding.contactList.adapter = adapter
        return adapter
    }

    private fun initUI() {
        initAdapter()
        binding.fab.setOnClickListener {
            it.findNavController().navigate(R.id.action_contactsListFragment_to_addContactFragment, null, OPTIONS)
        }

        viewModel.onUpdateContactList().observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar?.title = "Contacts"
    }
}
