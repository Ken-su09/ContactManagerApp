package com.suonk.contactmanagerapp.ui.adapters

import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.suonk.contactmanagerapp.databinding.ItemContactBinding
import com.suonk.contactmanagerapp.models.data.Contact
import com.suonk.contactmanagerapp.ui.activity.MainActivity
import com.suonk.contactmanagerapp.ui.fragments.main_pages.ContactsListFragment
import com.suonk.contactmanagerapp.ui.fragments.main_pages.FavoriteContactsFragment

class ContactsListAdapter(private val fragment: Fragment) :
    ListAdapter<Contact, ContactsListAdapter.ViewHolder>(ContactsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, fragment)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = getItem(position)
        holder.bind(contact, position)
    }

    inner class ViewHolder(
        private val binding: ItemContactBinding,
        private val fragment: Fragment
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(contact: Contact, position: Int) {
            binding.userName.text = "${contact.firstName} ${contact.lastName}"
            binding.userImage.setImageDrawable(contact.img!!.toDrawable(Resources.getSystem()))

            binding.root.setOnClickListener {
                when (fragment.tag) {
                    "f0" -> (fragment as ContactsListFragment).goToContactDetails(position)
                    "f1" -> (fragment as FavoriteContactsFragment).goToContactDetails(position)
                }
            }
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