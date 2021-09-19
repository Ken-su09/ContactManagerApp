package com.suonk.contactmanagerapp.ui.fragments.main_pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.suonk.contactmanagerapp.R
import com.suonk.contactmanagerapp.databinding.FragmentMessagesListBinding
import com.suonk.contactmanagerapp.models.data.Contact
import com.suonk.contactmanagerapp.models.data.Message
import com.suonk.contactmanagerapp.ui.activity.MainActivity
import com.suonk.contactmanagerapp.ui.adapters.ContactsListAdapter
import com.suonk.contactmanagerapp.ui.adapters.MessagesListAdapter
import com.suonk.contactmanagerapp.viewmodels.ContactManagerViewModel
import com.suonk.contactmanagerapp.viewmodels.MessageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MessagesListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var messagesListAdapter: MessagesListAdapter
    private lateinit var newMessageButton: FloatingActionButton

    private val messageViewModel: MessageViewModel by activityViewModels()
    private val contactViewModel: ContactManagerViewModel by activityViewModels()

    private val listOfIds = mutableListOf<Int>()
    private val listOfMessages = mutableListOf<Message>()
    private val listOfContacts = mutableListOf<Contact>()

    private var binding: FragmentMessagesListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMessagesListBinding.inflate(inflater, container, false)
        initializeUI()
        return binding!!.root
    }

    private fun initializeUI() {
        recyclerView = binding!!.recyclerView

        messagesListAdapter = MessagesListAdapter(this)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        recyclerView.apply {
            this.adapter = messagesListAdapter
            getAllLastMessagesFromDatabase()
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun getAllLastMessagesFromDatabase() {
        contactViewModel.allContactsAlphabet.observe(viewLifecycleOwner, { contacts ->
            listOfContacts.apply {
                clear()
                addAll(contacts)
            }
        })


        messageViewModel.allLastMessages.observe(viewLifecycleOwner, { lastMessages ->
            listOfIds.clear()
            CoroutineScope(Dispatchers.Default).launch {
                for (i in lastMessages.indices) {
                    for (y in listOfContacts.indices) {
                        if (!listOfIds.contains(listOfContacts[y].id)) {
                            if (lastMessages[i].message_contact_id == listOfContacts[y].id) {
                                listOfIds.add(lastMessages[i].message_contact_id)
                                listOfMessages.add(lastMessages[i])
                                break
                            }

                        }
                    }
                }

                messagesListAdapter.submitList(listOfMessages)
            }
        })
    }

    fun goToAllMessages(position: Int) {
        messageViewModel.apply {
            setContactIdLiveData(listOfMessages[position].id)
        }
        (activity as MainActivity).startAllMessagesContact()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}