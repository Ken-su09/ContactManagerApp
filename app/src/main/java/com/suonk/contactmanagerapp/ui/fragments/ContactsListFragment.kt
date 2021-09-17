package com.suonk.contactmanagerapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.suonk.contactmanagerapp.databinding.FragmentContactsListBinding
import com.suonk.contactmanagerapp.ui.activity.MainActivity
import com.suonk.contactmanagerapp.ui.adapters.ContactsListAdapter
import com.suonk.contactmanagerapp.viewmodels.ContactManagerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactsListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var editText: AppCompatEditText
    private var adapter = ContactsListAdapter()
    private lateinit var addNewContactButton: FloatingActionButton
    private lateinit var importContactsButton: AppCompatImageView

    private val viewModel: ContactManagerViewModel by activityViewModels()

    private var binding: FragmentContactsListBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
        editText = binding!!.searchContactEditText
        addNewContactButton = binding!!.addNewContactButton
        importContactsButton = binding!!.importContacts

        initRecyclerView()

        addNewContactButton.setOnClickListener {
            (activity as MainActivity).startContactDetails()
        }
    }

    private fun initRecyclerView() {
        recyclerView.adapter = adapter
        getContactListFromDatabase()
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun getContactListFromDatabase() {
        viewModel.allContactsAlphabet.observe(viewLifecycleOwner, { contacts ->
            contacts.let {
                adapter.submitList(contacts)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}