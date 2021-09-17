package com.suonk.contactmanagerapp.hilt

import android.content.Context
import androidx.room.Room
import com.suonk.contactmanagerapp.models.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context, AppDatabase::class.java, "contact_db"
        )
            .allowMainThreadQueries()
            .addMigrations()
            .build()

    @Provides
    fun provideDao(database: AppDatabase) = database.contactDao()
}