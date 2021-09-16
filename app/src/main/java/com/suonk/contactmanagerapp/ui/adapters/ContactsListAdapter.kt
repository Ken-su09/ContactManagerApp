package com.suonk.contactmanagerapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.suonk.contactmanagerapp.databinding.ItemContactBinding
import com.suonk.contactmanagerapp.models.data.Contact

class ContactsListAdapter :
    ListAdapter<Contact, ContactsListAdapter.ViewHolder>(ContactsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemContactBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = getItem(position)
        holder.bind(contact)
    }

    inner class ViewHolder(private val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(contact: Contact) {
            binding.userName.text = "${contact.firstName} ${contact.lastName}"
//            binding.userName.text = "${contact.firstName} ${contact.lastName}"
        }
    }

    class ContactsComparator : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.firstName == newItem.firstName &&
                    oldItem.lastName == newItem.lastName &&
                    oldItem.email == newItem.email &&
                    oldItem.phoneNumber == newItem.phoneNumber
        }
    }
}