package com.suonk.contactmanagerapp.ui.fragments.main_pages

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.suonk.contactmanagerapp.databinding.FragmentContactsListBinding
import com.suonk.contactmanagerapp.models.data.Contact
import com.suonk.contactmanagerapp.ui.activity.MainActivity
import com.suonk.contactmanagerapp.ui.adapters.ContactsListAdapter
import com.suonk.contactmanagerapp.viewmodels.ContactManagerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactsListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var contactsListAdapter: ContactsListAdapter
    private lateinit var addNewContactButton: FloatingActionButton

    private val viewModel: ContactManagerViewModel by activityViewModels()
    private val listOfContacts = mutableListOf<Contact>()

    private lateinit var sharedPreferences: SharedPreferences

    private var binding: FragmentContactsListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactsListBinding.inflate(inflater, container, false)
        initializeUI()
        return binding!!.root
    }

    private fun initializeUI() {
        recyclerView = binding!!.recyclerView
        addNewContactButton = binding!!.addNewContactButton

        addNewContactButton.setOnClickListener {
            (activity as MainActivity).startAddNewContact()
        }

        sharedPreferences = (activity as MainActivity).getSharedPreferences(
            "is_contact_deleted",
            Context.MODE_PRIVATE
        )

        getContactsListFilterText()

        contactsListAdapter = ContactsListAdapter(this)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        recyclerView.apply {
            this.adapter = contactsListAdapter
            getContactListFromDatabase()
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun getContactListFromDatabase() {
        viewModel.allContactsAlphabet.observe(viewLifecycleOwner, { contacts ->
            listOfContacts.clear()
            listOfContacts.addAll(contacts)
            contacts.let {
                contactsListAdapter.submitList(listOfContacts)
            }
        })
    }

    private fun getContactsListFilterText() {
        viewModel.allContactsAlphabet.observe(viewLifecycleOwner, { contacts ->
            contacts.let {
                viewModel.searchBarText.observe(viewLifecycleOwner, { searchBarText ->
                    if (searchBarText != "" || searchBarText.isEmpty()) {
                        val listOfContactsFilter = mutableListOf<Contact>()
                        for (i in contacts.indices) {
                            if (contacts[i].firstName.contains(searchBarText) ||
                                contacts[i].lastName.contains(searchBarText) ||
                                contacts[i].firstName.contains(searchBarText.lowercase()) ||
                                contacts[i].lastName.contains(searchBarText.lowercase())
                            ) {
                                listOfContactsFilter.add(contacts[i])
                            }
                        }
                        listOfContacts.clear()
                        listOfContacts.addAll(listOfContactsFilter)
                        contactsListAdapter.submitList(listOfContactsFilter)
                    }
                })
            }
        })

        val edit = sharedPreferences.edit()
        edit.putBoolean("is_contact_deleted", false)
        edit.apply()
    }

    fun goToContactDetails(position: Int) {
        viewModel.apply {
            setContactLiveData(listOfContacts[position])
            setSearchBarText("")
        }
        (activity as MainActivity).startContactDetails()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}