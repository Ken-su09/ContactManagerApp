package com.suonk.contactmanagerapp.viewmodels

import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.suonk.contactmanagerapp.models.data.Contact
import com.suonk.contactmanagerapp.repositories.ContactManagerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactManagerViewModel @Inject constructor(private val repository: ContactManagerRepository) :
    ViewModel() {

    //region ===================================== Database Operations ======================================

    val allContactsAlphabet = repository.allContactsAlphabet.asLiveData()
    val allContactsInverseAlphabet = repository.allContactsInverseAlphabet.asLiveData()
    val allFavoriteContactsAlphabet = repository.allFavoriteContactsAlphabet.asLiveData()
    val allFavoriteContactsInverseAlphabet =
        repository.allFavoriteContactsInverseAlphabet.asLiveData()

    fun getContactById(contactId: Int) = repository.getContactById(contactId)

    fun addNewContact(contact: Contact) = viewModelScope.launch {
        repository.addNewContact(contact)
    }

    fun updateContact(contact: Contact) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateContact(contact)
    }

    fun deleteContact(contact: Contact) = viewModelScope.launch {
        repository.deleteContact(contact)
    }
    //endregion

    //region ==================================== Data Between Fragments ====================================

    val searchBarText = MutableLiveData<String>()
    fun setSearchBarText(text: String) {
        searchBarText.value = text
    }

    val contactLiveData = MutableLiveData<Contact>()
    fun setContactLiveData(contact: Contact) {
        contactLiveData.value = contact
    }

    //endregion
}