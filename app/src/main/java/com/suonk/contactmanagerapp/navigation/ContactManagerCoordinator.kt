package com.suonk.contactmanagerapp.navigation

import javax.inject.Inject

class ContactManagerCoordinator @Inject constructor(private val navigator: Navigator) {

    fun showSplashScreen() {
        navigator.showSplashScreen()
    }

    fun showContactsList() {
        navigator.showContactsList()
    }

    fun showContactDetails() {
        navigator.showContactDetails()
    }

    fun showEditContact() {
        navigator.showEditContact()
    }

    fun showAddNewContact() {
        navigator.showAddNewContact()
    }

    fun showAllMessagesContact() {
        navigator.startAllMessagesContact()
    }
}