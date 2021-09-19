package com.suonk.contactmanagerapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.suonk.contactmanagerapp.models.data.Contact
import com.suonk.contactmanagerapp.models.data.Message
import com.suonk.contactmanagerapp.repositories.MessageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(private val repository: MessageRepository) :
    ViewModel() {

    val allLastMessages = repository.getAllLastMessages.asLiveData()

    fun getAllMessagesByContactId(contactId: Int) =
        repository.getAllMessagesByContactId(contactId).asLiveData()

    fun addNewMessage(message: Message) = viewModelScope.launch {
        repository.addNewMessage(message)
    }

    fun updateMessage(message: Message) = viewModelScope.launch {
        repository.updateMessage(message)
    }

    fun deleteMessage(message: Message) = viewModelScope.launch {
        repository.deleteMessage(message)
    }

    //region ==================================== Data Between Fragments ====================================

    val contactIdLiveData = MutableLiveData<Int>()
    fun setContactIdLiveData(id: Int) {
        contactIdLiveData.value = id
    }

    //endregion
}