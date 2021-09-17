package com.suonk.contactmanagerapp.ui.activity

import android.Manifest
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.ContactsContract
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.viewpager.widget.PagerAdapter
import com.suonk.contactmanagerapp.R
import com.suonk.contactmanagerapp.databinding.ActivityMainBinding
import com.suonk.contactmanagerapp.models.data.Contact
import com.suonk.contactmanagerapp.navigation.ContactManagerCoordinator
import com.suonk.contactmanagerapp.navigation.Navigator
import com.suonk.contactmanagerapp.viewmodels.ContactManagerViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        const val PERMISSIONS_REQUEST_READ_CONTACTS = 100
    }

    @Inject
    lateinit var navigator: Navigator

    private lateinit var coordinator: ContactManagerCoordinator
    private lateinit var binding: ActivityMainBinding

    private lateinit var sharedPreferences: SharedPreferences
    private var isContactsAlreadyImported = false

    private val viewModel: ContactManagerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigator.activity = this
        coordinator = ContactManagerCoordinator(navigator)

        sharedPreferences = getSharedPreferences("contacts_already_imported", Context.MODE_PRIVATE)
        isContactsAlreadyImported = sharedPreferences.getBoolean("contacts_already_imported", false)

        startSplashScreen()
        Handler(Looper.getMainLooper()).postDelayed({
            startContactsList()
        }, 5000)
    }

    private fun startSplashScreen() {
        coordinator.showSplashScreen()
    }

    private fun startContactsList() {
        if (!isContactsAlreadyImported) {
            loadContacts()
        }
        coordinator.showContactsList()
    }

    fun startContactDetails() {
        coordinator.showContactDetails()
    }

    fun startAddNewContact() {
        coordinator.showAddNewContact()
    }

    private fun loadContacts() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(
                Manifest.permission.READ_CONTACTS
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.READ_CONTACTS),
                PERMISSIONS_REQUEST_READ_CONTACTS
            )
        } else {
            getContacts()
            val edit = sharedPreferences.edit()
            edit.putBoolean("contacts_already_imported", true)
            edit.apply()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadContacts()
            }
        }

    }

    private fun getContacts() {
        val resolver: ContentResolver = contentResolver
        val cursor = resolver.query(
            ContactsContract.Contacts.CONTENT_URI, null, null, null,
            null
        )

        if (cursor!!.count > 0) {
            while (cursor.moveToNext()) {
                val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val name =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val phoneNumber = (cursor.getString(
                    cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)
                )).toInt()
                var phoneNumValue = ""

                if (phoneNumber > 0) {
                    val cursorPhone = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                        arrayOf(id),
                        null
                    )

                    if (cursorPhone!!.count > 0) {
                        while (cursorPhone.moveToNext()) {
                            phoneNumValue = cursorPhone.getString(
                                cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                            )
                        }
                    }
                    cursorPhone.close()
                }

                val isFavorite =
                    cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.STARRED))
                Log.i("MainActivity", "$isFavorite")

                var bitmap = ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_user_icon,
                    null
                )!!.toBitmap()

                try {
                    if (id != null) {
                        val inputStream: InputStream? =
                            ContactsContract.Contacts.openContactPhotoInputStream(
                                contentResolver,
                                ContentUris.withAppendedId(
                                    ContactsContract.Contacts.CONTENT_URI,
                                    cursor.getColumnIndex(ContactsContract.Contacts._ID).toLong()
                                )
                            )
                        if (inputStream != null) {
                            bitmap = BitmapFactory.decodeStream(inputStream)
                            inputStream.close()
                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                val contact = Contact(
                    name, "", bitmap, phoneNumValue, "", isFavorite
                )
                viewModel.addNewContact(contact)
            }
        }

        cursor.close()
    }

    override fun onDestroy() {
        super.onDestroy()
        navigator.activity = null
    }
}