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

class MessagesListAdapter(private val fragment: Fragment) :
    ListAdapter<Message, MessagesListAdapter.ViewHolder>(ContactsComparator()) {

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