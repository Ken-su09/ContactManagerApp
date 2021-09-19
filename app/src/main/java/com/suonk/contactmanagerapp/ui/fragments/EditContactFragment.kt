package com.suonk.contactmanagerapp.ui.fragments

import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.suonk.contactmanagerapp.R
import com.suonk.contactmanagerapp.databinding.FragmentEditContactBinding
import com.suonk.contactmanagerapp.models.data.Contact
import com.suonk.contactmanagerapp.ui.activity.MainActivity
import com.suonk.contactmanagerapp.viewmodels.ContactManagerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditContactFragment : Fragment() {

    private var binding: FragmentEditContactBinding? = null
    private val viewModel: ContactManagerViewModel by activityViewModels()

    private var isFavorite = 0
    private var contactId = -1

    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var editContact: Contact

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditContactBinding.inflate(inflater, container, false)
        initializeUI()
        return binding!!.root
    }

    private fun initializeUI() {
        getContactFromViewModel()
        changeImageClick()
        favoriteIconClick()
        updateUserClick()
        deleteContactClick()

        sharedPreferences = (activity as MainActivity).getSharedPreferences(
            "is_contact_deleted",
            Context.MODE_PRIVATE
        )
    }

    private fun getContactFromViewModel() {
        viewModel.contactLiveData.observe(viewLifecycleOwner, { contact ->
            editContact = contact
            binding!!.apply {
                userImage.setImageDrawable(contact.img!!.toDrawable(Resources.getSystem()))
                userName.text = "${contact.firstName} ${contact.lastName}"
                contactNameValue.setText("${contact.firstName} ${contact.lastName}")
                contactEmailValue.setText("${contact.email}")
                contactPhoneNumberValue.setText("${contact.phoneNumber}")

                isFavorite = contact.isFavorite
                if (contact.isFavorite == 1) {
                    favoriteContact.visibility = View.VISIBLE
                    noFavoriteContact.visibility = View.INVISIBLE
                } else {
                    favoriteContact.visibility = View.INVISIBLE
                    noFavoriteContact.visibility = View.VISIBLE
                }

                contactId = contact.id
            }
        })
    }

    //region ============================================ Clicks ============================================

    private fun changeImageClick() {
        binding!!.userImage.setOnClickListener {
            (activity as MainActivity).openGalleryForImage(binding!!.userImage)
        }
    }

    private fun favoriteIconClick() {
        binding!!.apply {
            favoriteContact.setOnClickListener {
                favoriteContact.visibility = View.INVISIBLE
                noFavoriteContact.visibility = View.VISIBLE
                isFavorite = 0
            }
            noFavoriteContact.setOnClickListener {
                favoriteContact.visibility = View.VISIBLE
                noFavoriteContact.visibility = View.INVISIBLE
                isFavorite = 1
            }
        }
    }

    private fun updateUserClick() {
        binding!!.validateContact.setOnClickListener {
            if (checkIfFieldEmpty()) {
                val contact = Contact(
                    binding!!.contactNameValue.text.toString(),
                    "",
                    binding!!.userImage.drawable.toBitmap(),
                    binding!!.contactPhoneNumberValue.text.toString(),
                    binding!!.contactEmailValue.text.toString(),
                    isFavorite,
                    contactId
                )
                viewModel.updateContact(contact)
                viewModel.setContactLiveData(contact)
                (activity as MainActivity).startContactDetails()
            } else {
                Toast.makeText(context, "Field can not be empty !", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun deleteContactClick() {
        binding!!.deleteContact.setOnClickListener {
            MaterialAlertDialogBuilder(
                requireContext(),
                R.style.AlertDialog
            )
                .setTitle("You are gonna delete this contact")
                .setPositiveButton("Delete") { _, _ ->
                    viewModel.deleteContact(editContact)

                    val edit = sharedPreferences.edit()
                    edit.putBoolean("is_contact_deleted", true)
                    edit.apply()

                    requireActivity().supportFragmentManager.popBackStack()
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                    dialog.cancel()
                }
                .show()
        }
    }

    //endregion

    private fun checkIfFieldEmpty(): Boolean {
        return binding!!.contactNameValue.text!!.isNotEmpty() && binding!!.contactPhoneNumberValue.text!!.isNotEmpty()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}