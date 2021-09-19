package com.suonk.contactmanagerapp.models.dao

import androidx.room.*
import com.suonk.contactmanagerapp.models.data.Contact
import com.suonk.contactmanagerapp.models.data.Message
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {

    /**
     * getAllLastMessages() = contact1, contact2, contact3...
     */
    @Query("SELECT * FROM message_db ORDER BY date ASC")
    fun getAllLastMessages(): Flow<List<Message>>

    /**
     * getMessagesByContactId(contactId) = message1, message2, message3...
     */
    @Query("SELECT * FROM message_db WHERE message_contact_id = :contact_id")
    fun getAllMessagesByContactId(contact_id: Int): Flow<List<Message>>

    /**
     * addNewMessage(contact)
     */
    @Insert
    suspend fun addNewMessage(message: Message)

    /**
     * updateMessage(message)
     */
    @Update
    suspend fun updateMessage(message: Message)

    /**
     * deleteMessage(message)
     */
    @Delete
    suspend fun deleteMessage(message: Message)
}