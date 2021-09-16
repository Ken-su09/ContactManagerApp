package com.suonk.contactmanagerapp.viewmodels

import androidx.annotation.WorkerThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.suonk.contactmanagerapp.models.data.Contact
import com.suonk.contactmanagerapp.repositories.ContactManagerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactManagerViewModel @Inject constructor(private val repository: ContactManagerRepository) :
    ViewModel() {

    val allContactsAlphabet = repository.allContactsAlphabet.asLiveData()
    val allContactsInverseAlphabet = repository.allContactsInverseAlphabet.asLiveData()

    fun getContactById(contactId: Int) = repository.getContactById(contactId)

    fun addNewContact(contact: Contact) = viewModelScope.launch {
        repository.addNewContact(contact)
    }

    suspend fun updateContact(contact: Contact) = viewModelScope.launch {
        repository.updateContact(contact)
    }

    suspend fun deleteContact(contact: Contact) = viewModelScope.launch {
        repository.deleteContact(contact)
    }
}