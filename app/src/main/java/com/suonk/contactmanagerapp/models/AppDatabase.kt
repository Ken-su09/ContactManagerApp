package com.suonk.contactmanagerapp.models

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.suonk.contactmanagerapp.models.dao.ContactDao
import com.suonk.contactmanagerapp.models.data.Contact
import com.suonk.contactmanagerapp.utils.Converters

@Database(entities = [Contact::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao
}