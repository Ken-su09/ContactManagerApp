package com.suonk.contactmanagerapp.ui.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.net.Uri
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

    private lateinit var sharedPreferences: SharedPreferences
    private var isContactDeleted = false

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
        callLayoutClick()
        backToContactsList()

        sharedPreferences = (activity as MainActivity).getSharedPreferences(
            "is_contact_deleted",
            Context.MODE_PRIVATE
        )
        isContactDeleted = sharedPreferences.getBoolean("is_contact_deleted", false)

        if (isContactDeleted) {
            contactIsDeleted()
        }
    }

    private fun getContactFromViewModel() {
        viewModel.contactLiveData.observe(viewLifecycleOwner, { contact ->
            binding!!.apply {
                userImage.setImageDrawable(contact.img!!.toDrawable(Resources.getSystem()))
                userName.text = "${contact.firstName} ${contact.lastName}"
                contactNameValue.text = "${contact.firstName} ${contact.lastName}"
                contactEmailValue.text = "${contact.email}"
                contactPhoneNumberValue.text = "${contact.phoneNumber}"
            }
        })
    }

    //region ============================================ Clicks ============================================

    private fun whatsappLayoutClick() {
        binding!!.whatsappLayout.setOnClickListener {
            if (binding!!.contactPhoneNumberValue.text.isNotEmpty()) {
                openWhatsapp(binding!!.contactPhoneNumberValue.text.toString())
            }
        }
    }

    private fun editContactClick() {
        binding!!.editLayout.setOnClickListener {
            (activity as MainActivity).startEditContact()
        }
    }

    private fun backToContactsList() {
        binding!!.backToContactsList.setOnClickListener {
            (activity as MainActivity).startContactsList()
        }
    }

    private fun callLayoutClick() {
        binding!!.callLayout.setOnClickListener {
            if (binding!!.contactPhoneNumberValue.text.isNotEmpty()) {
                (activity as MainActivity).phoneCall(binding!!.contactPhoneNumberValue.text.toString())
            }
        }
    }

    //endregion

    //region ==================================== Phone Functionalities =====================================

    private fun contactIsDeleted() {
        requireActivity().supportFragmentManager.popBackStack()

        val edit = sharedPreferences.edit()
        edit.putBoolean("is_contact_deleted", false)
        edit.apply()
    }

    private fun openWhatsapp(phoneNumber: String) {
        val url =
            "https://api.whatsapp.com/send?phone=${convertNumberToWhatsappNumber(phoneNumber)}"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    private fun convertNumberToWhatsappNumber(phoneNumber: String): String {
        if (phoneNumber[0] == '0') {
            return "+33$phoneNumber"
        }
        return phoneNumber
    }

    //endregion

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}