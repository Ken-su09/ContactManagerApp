package com.suonk.contactmanagerapp.models

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.suonk.contactmanagerapp.models.dao.ContactDao
import com.suonk.contactmanagerapp.models.data.Contact
import com.suonk.contactmanagerapp.utils.Converters

@Database(
    entities = [Contact::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao

    var MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE `contact_db` ADD isFavorite INT NOT NULL DEFAULT''")
        }
    }
}