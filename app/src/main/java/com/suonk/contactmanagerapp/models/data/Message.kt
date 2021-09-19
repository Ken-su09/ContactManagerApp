package com.suonk.contactmanagerapp.models.data

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message_db")
data class Message(
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "img")
    val img: Bitmap? = null,
    @ColumnInfo(name = "message_contact_id")
    val message_contact_id: Int,
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)
