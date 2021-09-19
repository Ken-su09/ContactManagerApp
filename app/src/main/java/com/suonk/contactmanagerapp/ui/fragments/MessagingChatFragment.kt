package com.suonk.contactmanagerapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.suonk.contactmanagerapp.R
import com.suonk.contactmanagerapp.databinding.FragmentMessagingChatBinding
import com.suonk.contactmanagerapp.models.data.Contact
import com.suonk.contactmanagerapp.models.data.Message
import com.suonk.contactmanagerapp.ui.adapters.MessagesListAdapter
import com.suonk.contactmanagerapp.viewmodels.ContactManagerViewModel
import com.suonk.contactmanagerapp.viewmodels.MessageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MessagingChatFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var messagesListAdapter: MessagesListAdapter
    private lateinit var newMessageButton: FloatingActionButton

    private val messageViewModel: MessageViewModel by activityViewModels()
    private val contactViewModel: ContactManagerViewModel by activityViewModels()

    private var binding: FragmentMessagingChatBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMessagingChatBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}