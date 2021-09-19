package com.suonk.contactmanagerapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.suonk.contactmanagerapp.databinding.FragmentAddNewContactBinding
import com.suonk.contactmanagerapp.models.data.Contact
import com.suonk.contactmanagerapp.ui.activity.MainActivity
import com.suonk.contactmanagerapp.viewmodels.ContactManagerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNewContactFragment : Fragment() {

    private var binding: FragmentAddNewContactBinding? = null
    private val viewModel: ContactManagerViewModel by activityViewModels()

    private var isFavorite = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNewContactBinding.inflate(inflater, container, false)
        initializeUI()
        return binding!!.root
    }

    private fun initializeUI() {
        changeImageClick()
        favoriteIconClick()
        saveUserClick()
        backToContactListClick()
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

    private fun saveUserClick() {
        binding!!.validateContact.setOnClickListener {
            if (checkIfFieldEmpty()) {
                val contact = Contact(
                    binding!!.contactNameValue.text.toString(),
                    "",
                    binding!!.userImage.drawable.toBitmap(),
                    binding!!.contactPhoneNumberValue.text.toString(),
                    binding!!.contactEmailValue.text.toString(),
                    isFavorite
                )
                viewModel.addNewContact(contact)
                viewModel.setContactLiveData(contact)
                (activity as MainActivity).startContactDetails()
            } else {
                Toast.makeText(context, "Field can not be empty !", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun backToContactListClick() {
        binding!!.backToContactsList.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun checkIfFieldEmpty(): Boolean {
        return binding!!.contactNameValue.text!!.isNotEmpty() && binding!!.contactPhoneNumberValue.text!!.isNotEmpty()
    }

    //endregion

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}