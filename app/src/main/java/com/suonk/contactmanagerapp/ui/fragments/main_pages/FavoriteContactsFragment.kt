package com.suonk.contactmanagerapp.ui.fragments.main_pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.suonk.contactmanagerapp.R
import com.suonk.contactmanagerapp.databinding.FragmentFavoriteContactsBinding
import com.suonk.contactmanagerapp.databinding.FragmentGroupsBinding
import com.suonk.contactmanagerapp.models.data.Contact
import com.suonk.contactmanagerapp.ui.activity.MainActivity
import com.suonk.contactmanagerapp.ui.adapters.ContactsListAdapter
import com.suonk.contactmanagerapp.viewmodels.ContactManagerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteContactsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var contactsListAdapter: ContactsListAdapter
    private lateinit var addNewContactButton: FloatingActionButton

    private val viewModel: ContactManagerViewModel by activityViewModels()
    private val listOfContacts = mutableListOf<Contact>()

    private var binding: FragmentFavoriteContactsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteContactsBinding.inflate(inflater, container, false)
        initializeUI()
        return binding!!.root
    }

    private fun initializeUI() {
        recyclerView = binding!!.recyclerView
        addNewContactButton = binding!!.addNewContactButton
        contactsListAdapter = ContactsListAdapter(this)

        initRecyclerView()

        getContactsListFilterText()
    }

    private fun initRecyclerView() {
        recyclerView.adapter = contactsListAdapter
        getFavoriteContactsFromDatabase()
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun getFavoriteContactsFromDatabase() {
        viewModel.allFavoriteContactsAlphabet.observe(viewLifecycleOwner, { favoriteContacts ->
            listOfContacts.clear()
            listOfContacts.addAll(favoriteContacts)
            favoriteContacts.let {
                contactsListAdapter.submitList(favoriteContacts)
            }
        })
    }

    private fun getContactsListFilterText() {
        viewModel.allFavoriteContactsAlphabet.observe(viewLifecycleOwner, { contacts ->
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