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
import com.suonk.contactmanagerapp.ui.adapters.ContactsListAdapter
import com.suonk.contactmanagerapp.viewmodels.ContactManagerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteContactsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var editText: AppCompatEditText
    private var adapter = ContactsListAdapter()
    private lateinit var addNewContactButton: FloatingActionButton

    private val viewModel: ContactManagerViewModel by activityViewModels()

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
        recyclerView = binding!!.recyclerView
        recyclerView = binding!!.recyclerView

        initRecyclerView()
    }

    private fun initRecyclerView() {
        recyclerView.adapter = adapter
        getFavoriteContactsFromDatabase()
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun getFavoriteContactsFromDatabase() {
        viewModel.allFavoriteContactsAlphabet.observe(viewLifecycleOwner, { favoriteContacts ->
            favoriteContacts.let {
                adapter.submitList(favoriteContacts)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}