package com.suonk.contactmanagerapp.ui.fragments

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.suonk.contactmanagerapp.databinding.FragmentContactDetailBinding
import com.suonk.contactmanagerapp.viewmodels.ContactManagerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactDetailsFragment : Fragment() {

    private var binding: FragmentContactDetailBinding? = null
    private val viewModel: ContactManagerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactDetailBinding.inflate(inflater, container, false)
        initializeUI()
        return binding!!.root
    }

    private fun initializeUI() {
        getContactFromViewModel()
    }

    private fun getContactFromViewModel() {
        viewModel.contactLiveData.observe(viewLifecycleOwner, { contact ->
            binding!!.userImage.setImageDrawable(contact.img!!.toDrawable(Resources.getSystem()))
            binding!!.userName.text = "${contact.firstName} ${contact.lastName}"
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}