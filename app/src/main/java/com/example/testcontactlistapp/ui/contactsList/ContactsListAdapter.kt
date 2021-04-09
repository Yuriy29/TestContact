package com.example.testcontactlistapp.ui.contactsList

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testcontactlistapp.data.ContactEntity
import com.example.testcontactlistapp.databinding.ListItemContactsBinding
import kotlinx.android.synthetic.main.list_item_contacts.view.*

class ContactsAdapter(val context: Context, val contactsClickListener: ContactsClickListener): ListAdapter<ContactEntity, RecyclerView.ViewHolder>
    (ContactListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ContactViewHolder(ListItemContactsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentContact = getItem(position)

        holder.itemView.phoneImageButton.setOnClickListener{
            val uri = "tel:" + currentContact.number?.firstOrNull()
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse(uri)
            context.startActivity(intent)
        }

        holder.itemView.emailImageButton.setOnClickListener {
            val to = currentContact.email

            val email = Intent(Intent.ACTION_SEND)
            email.putExtra(Intent.EXTRA_EMAIL, arrayOf<String>(to?.firstOrNull() ?: ""))

            email.type = "email"
            context.startActivity(Intent.createChooser(email, "Choose an Email client :"))
        }

        (holder as ContactViewHolder).bind(contactsClickListener, currentContact)
    }

    class ContactViewHolder(private val binding: ListItemContactsBinding) :
        RecyclerView.ViewHolder(binding.root){


        fun bind(contactListener: ContactsClickListener, item: ContactEntity){
            binding.apply {
                contactClickListener = contactListener
                contact = item
                executePendingBindings()
            }
        }
    }
}

private class ContactListDiffCallback : DiffUtil.ItemCallback<ContactEntity>(){
    override fun areItemsTheSame(oldItem: ContactEntity, newItem: ContactEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ContactEntity, newItem: ContactEntity): Boolean {
        return oldItem == newItem
    }
}

class ContactsClickListener(val clickListener: (contact: ContactEntity) -> Unit) {
    fun onClick(contact: ContactEntity) = clickListener(contact)
}