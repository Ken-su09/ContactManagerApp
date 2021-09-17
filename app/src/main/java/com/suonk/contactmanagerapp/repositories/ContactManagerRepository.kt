package com.suonk.contactmanagerapp.repositories

import androidx.annotation.WorkerThread
import com.suonk.contactmanagerapp.models.dao.ContactDao
import com.suonk.contactmanagerapp.models.data.Contact
import javax.inject.Inject

class ContactManagerRepository @Inject constructor(private val dao: ContactDao) {

    val allContactsAlphabet = dao.getAllContactsOrderByAlphabet()
    val allContactsInverseAlphabet = dao.getAllContactsOrderByInverseAlphabet()
    val allFavoriteContactsAlphabet = dao.getAllFavoriteContactsOrderByAlphabet()
    val allFavoriteContactsInverseAlphabet = dao.getAllFavoriteContactsOrderByInverseAlphabet()

    fun getContactById(contactId: Int) = dao.getContactById(contactId)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addNewContact(contact: Contact) {
        dao.addNewContact(contact)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateContact(contact: Contact) {
        dao.updateContact(contact)
    }

    @WorkerThread
    suspend fun deleteContact(contact: Contact) {
        dao.deleteContact(contact)
    }
}