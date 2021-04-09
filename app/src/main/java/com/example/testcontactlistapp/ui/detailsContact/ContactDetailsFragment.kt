package com.example.testcontactlistapp.ui.detailsContact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.testcontactlistapp.MainActivity
import com.example.testcontactlistapp.R
import com.example.testcontactlistapp.databinding.FragmentContactDetailsBinding
import com.example.testcontactlistapp.di.ViewModelProviderFactory
import com.example.testcontactlistapp.util.OPTIONS
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ContactDetailsFragment : DaggerFragment() {

    @Inject
    lateinit var factory: ViewModelProviderFactory

    private lateinit var viewModel: ContactDetailsViewModel
    private lateinit var args:ContactDetailsFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentContactDetailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_contact_details, container, false)

        binding.lifecycleOwner = this.viewLifecycleOwner
        args = ContactDetailsFragmentArgs.fromBundle(arguments!!)
        viewModel = ViewModelProviders.of(this, factory).get(ContactDetailsViewModel::class.java)
        viewModel.getContactByInt(args.id)

        initUI(binding)

        return binding.root
    }

    private fun initUI(binding: FragmentContactDetailsBinding) {
        viewModel.onUpdateContact().observe(this, Observer {
            binding.data.contact = it
            val listData = StringBuilder()
            val emailSize = it.email?.size ?: 1
            it.email?.forEachIndexed { index, it ->
                listData.append(it)
                if (emailSize - 1 != index) {
                    listData.append(", ")
                }
            }
            binding.data.email.text = listData.toString()
            listData.clear()
            val numberSize = it.number?.size ?: 1
            it.number?.forEachIndexed { index, it ->
                listData.append(it)
                if (numberSize - 1 != index) {
                    listData.append(", ")
                }
            }
            binding.data.phoneNumber.text = listData.toString()
        })

        binding.editContactFab.setOnClickListener {
            it.findNavController().navigate(
                ContactDetailsFragmentDirections
                    .actionContactDetailsFragmentToAddContactFragment(args.id)
            )
        }

        binding.deleteButton.setOnClickListener {
            viewModel.deleteContact(binding.data.contact)
            it.findNavController().navigate(
                ContactDetailsFragmentDirections
                    .actionContactDetailsFragmentToContactsListFragment()
            )
        }

        requireActivity().onBackPressedDispatcher.addCallback(this)
        {
            findNavController().navigate(
                ContactDetailsFragmentDirections.actionContactDetailsFragmentToContactsListFragment(),
                OPTIONS
            )
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar?.title = "Contact Details"
    }
}