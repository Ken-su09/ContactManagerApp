package com.suonk.contactmanagerapp.ui.fragments

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.suonk.contactmanagerapp.databinding.FragmentContactDetailsBinding
import com.suonk.contactmanagerapp.ui.activity.MainActivity
import com.suonk.contactmanagerapp.viewmodels.ContactManagerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactDetailsFragment : Fragment() {

    private var binding: FragmentContactDetailsBinding? = null
    private val viewModel: ContactManagerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactDetailsBinding.inflate(inflater, container, false)
        initializeUI()
        return binding!!.root
    }

    private fun initializeUI() {
        getContactFromViewModel()

        editContactClick()
        whatsappLayoutClick()
    }

    private fun getContactFromViewModel() {
        viewModel.contactLiveData.observe(viewLifecycleOwner, { contact ->
            binding!!.userImage.setImageDrawable(contact.img!!.toDrawable(Resources.getSystem()))
            binding!!.userName.text = "${contact.firstName} ${contact.lastName}"
            binding!!.contactNameValue.text = "${contact.firstName} ${contact.lastName}"
            binding!!.contactEmailValue.text = "${contact.email}"
            binding!!.contactPhoneNumberValue.text = "${contact.phoneNumber}"
        })
    }

    //region ============================================ Clicks ============================================

    private fun whatsappLayoutClick() {
        binding!!.whatsappLayout.setOnClickListener {
            if (binding!!.contactPhoneNumberValue.text.isNotEmpty())
                (activity as MainActivity).openWhatsapp(binding!!.contactPhoneNumberValue.text.toString())
        }
    }

    private fun editContactClick() {
        binding!!.editLayout.setOnClickListener {
            (activity as MainActivity).startEditContact()
        }
    }

    //endregion

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}