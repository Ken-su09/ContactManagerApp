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
import com.suonk.contactmanagerapp.models.data.Message
import com.suonk.contactmanagerapp.ui.activity.MainActivity
import com.suonk.contactmanagerapp.ui.fragments.main_pages.ContactsListFragment
import com.suonk.contactmanagerapp.ui.fragments.main_pages.FavoriteContactsFragment
import com.suonk.contactmanagerapp.ui.fragments.main_pages.MessagesListFragment

class AllMessagesAdapter(private val fragment: Fragment) :
    ListAdapter<Message, AllMessagesAdapter.ViewHolder>(ContactsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, fragment)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = getItem(position)
        holder.bind(message, position)
    }

    inner class ViewHolder(
        private val binding: ItemContactBinding,
        private val fragment: Fragment
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(message: Message, position: Int) {
            binding.root.setOnClickListener {
                Log.i("getContactsList", "${fragment.tag}")
                when (fragment.tag) {
                    "f0" -> (fragment as MessagesListFragment).goToAllMessages(position)
                }
            }
        }
    }

    class ContactsComparator : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return true
        }
    }
}