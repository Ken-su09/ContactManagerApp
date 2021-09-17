package com.suonk.contactmanagerapp.models.dao

import androidx.room.*
import com.suonk.contactmanagerapp.models.data.Contact
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {

    /**
     * getAllContactsOrderByAlphabet() = contact1, contact2, contact3...
     */
    @Query("SELECT * FROM contact_db ORDER BY firstName ASC")
    fun getAllContactsOrderByAlphabet(): Flow<List<Contact>>

    /**
     * getAllContactsOrderByInverseAlphabet() = contact1, contact2, contact3...
     */
    @Query("SELECT * FROM contact_db ORDER BY firstName DESC")
    fun getAllContactsOrderByInverseAlphabet(): Flow<List<Contact>>

    /**
     * getContactById() = contact
     */
    @Query("SELECT * FROM contact_db WHERE id = :contactId")
    fun getContactById(contactId: Int): Flow<Contact>

    /**
     * addNewContact(contact)
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewContact(contact: Contact)

    /**
     * updateContact(contact)
     */
    @Update
    suspend fun updateContact(contact: Contact)

    /**
     * deleteContact(contact)
     */
    @Delete
    suspend fun deleteContact(contact: Contact)
}