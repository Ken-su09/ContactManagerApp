package com.suonk.contactmanagerapp.navigation

import androidx.fragment.app.FragmentActivity
import com.suonk.contactmanagerapp.R
import com.suonk.contactmanagerapp.ui.fragments.AddNewContactFragment
import com.suonk.contactmanagerapp.ui.fragments.ContactDetailsFragment
import com.suonk.contactmanagerapp.ui.fragments.ContactsListFragment
import com.suonk.contactmanagerapp.ui.fragments.SplashScreenFragment
import javax.inject.Inject

class Navigator @Inject constructor(var activity: FragmentActivity?) {

    fun showSplashScreen() {
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view, SplashScreenFragment())
            .commit()
    }

    fun showContactsList() {
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view, ContactsListFragment())
            .commit()
    }

    fun showContactDetails() {
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view, ContactDetailsFragment())
            .commit()
    }

    fun showAddNewContact() {
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view, AddNewContactFragment())
            .commit()
    }
}