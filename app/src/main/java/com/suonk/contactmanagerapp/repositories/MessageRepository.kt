package com.suonk.contactmanagerapp.repositories

import androidx.annotation.WorkerThread
import com.suonk.contactmanagerapp.models.dao.MessageDao
import com.suonk.contactmanagerapp.models.data.Message
import javax.inject.Inject

class MessageRepository @Inject constructor(private val dao: MessageDao) {

    val getAllLastMessages = dao.getAllLastMessages()

    fun getAllMessagesByContactId(contactId: Int) = dao.getAllMessagesByContactId(contactId)

    @WorkerThread
    suspend fun addNewMessage(message: Message) {
        dao.addNewMessage(message)
    }

    @WorkerThread
    suspend fun updateMessage(message: Message) {
        dao.updateMessage(message)
    }

    @WorkerThread
    suspend fun deleteMessage(message: Message) {
        dao.deleteMessage(message)
    }
}