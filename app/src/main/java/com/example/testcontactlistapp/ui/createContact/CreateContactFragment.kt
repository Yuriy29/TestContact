package com.example.testcontactlistapp.ui.createContact


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.addCallback
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.testcontactlistapp.MainActivity
import com.example.testcontactlistapp.R
import com.example.testcontactlistapp.data.ContactEntity
import com.example.testcontactlistapp.databinding.FragmentCreateContactBinding
import com.example.testcontactlistapp.di.ViewModelProviderFactory
import com.example.testcontactlistapp.util.OPTIONS
import com.google.android.material.textfield.TextInputEditText
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class CreateContactFragment : DaggerFragment() {

    private lateinit var binding: FragmentCreateContactBinding

    @Inject
    lateinit var factory: ViewModelProviderFactory

    private lateinit var viewModel: CreateContactViewModel

    private lateinit var args: CreateContactFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_create_contact, container, false
        )
        binding.lifecycleOwner = this.viewLifecycleOwner
        viewModel = ViewModelProviders.of(this, factory).get(CreateContactViewModel::class.java)
        args = CreateContactFragmentArgs.fromBundle(arguments!!)

        initUI()

        return binding.root
    }

    private fun initUI() {
        if (args.id != -1) {
            viewModel.getContactById(args.id)
            viewModel.onUpdateContact().observe(this, Observer {
                binding.contact = it
                binding.phoneNumbersContainer.removeAllViews()
                binding.emailsContainer.removeAllViews()
                it.email?.forEach {
                    addEmailEditTextLayout(it)
                }
                it.number?.forEach {
                    addPhoneNumberEditTextLayout(it)
                }
            })
        }

        binding.firstNameEditText.doAfterTextChanged {
            viewModel.firstName = it.toString()
        }

        binding.lastNameEditText.doAfterTextChanged {
            viewModel.lastName = it.toString()
        }

        addPhoneNumberEditTextLayout()
        addEmailEditTextLayout()

        binding.saveButton.setOnClickListener {
            if (args.id == -1) {
                viewModel.saveContact(
                    ContactEntity(
                        viewModel.firstName,
                        viewModel.lastName,
                        viewModel.phoneNumbers.values.toList(),
                        viewModel.emails.values.toList()
                    )
                )
                it.findNavController().navigate(
                    CreateContactFragmentDirections
                        .actionAddContactFragmentToContactsFragment()
                )
            } else {
                viewModel.updateContact(
                    ContactEntity(
                        viewModel.firstName,
                        viewModel.lastName,
                        viewModel.phoneNumbers.values.toList(),
                        viewModel.emails.values.toList(),
                        args.id
                    )
                )
                it.findNavController().navigate(
                    CreateContactFragmentDirections
                        .actionAddContactFragmentToContactsFragment()
                )
            }
        }

        binding.cancelButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_addContactFragment_to_contactsFragment)
        }

        requireActivity().onBackPressedDispatcher.addCallback(this)
        {
            findNavController().navigate(
                CreateContactFragmentDirections.actionAddContactFragmentToContactsFragment(),
                OPTIONS
            )
        }
        binding.addPhoneTextView.setOnClickListener {
            addPhoneNumberEditTextLayout()
        }

        binding.addEmailTextView.setOnClickListener {
            addEmailEditTextLayout()
        }
    }

    private fun addPhoneNumberEditTextLayout(value: String? = null) {
        viewModel.keyDataList++
        val phoneLayout =
            LayoutInflater.from(context)
                .inflate(R.layout.item_phone_number, binding.containerLayout, false)
        value?.let {
            phoneLayout.findViewById<TextInputEditText>(R.id.phoneNumberEditText)
                .setText(it, TextView.BufferType.EDITABLE)
        }
        phoneLayout.findViewById<TextInputEditText>(R.id.phoneNumberEditText).doAfterTextChanged {
            viewModel.phoneNumbers.put(viewModel.keyDataList, it.toString())
        }
        binding.phoneNumbersContainer.addView(phoneLayout)
    }

    private fun addEmailEditTextLayout(value: String? = null) {
        viewModel.keyDataList++
        val emailLayout =
            LayoutInflater.from(context)
                .inflate(R.layout.item_email, binding.containerLayout, false)
        value?.let {
            emailLayout.findViewById<TextInputEditText>(R.id.emailEditText)
                .setText(it, TextView.BufferType.EDITABLE)
        }
        emailLayout.findViewById<TextInputEditText>(R.id.emailEditText).doAfterTextChanged {
            viewModel.emails.put(viewModel.keyDataList, it.toString())
        }
        binding.emailsContainer.addView(emailLayout)
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar?.title =
            getString(R.string.add_new_contact_title)
    }
}
