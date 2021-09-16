package com.suonk.contactmanagerapp.ui.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.suonk.contactmanagerapp.databinding.ActivityMainBinding
import com.suonk.contactmanagerapp.navigation.ContactManagerCoordinator
import com.suonk.contactmanagerapp.navigation.Navigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    private lateinit var navigator: Navigator
    private lateinit var coordinator: ContactManagerCoordinator
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigator.activity = this
        coordinator = ContactManagerCoordinator(navigator)

        startSplashScreen()
        Handler(Looper.getMainLooper()).postDelayed({
            startContactsList()
        }, 5000)
    }

    private fun startSplashScreen() {
        coordinator.showSplashScreen()
    }

    private fun startContactsList() {
        coordinator.showContactsList()
    }

    private fun startContactDetails() {
        coordinator.showContactDetails()
    }

    private fun startAddNewContact() {
        coordinator.showAddNewContact()
    }

    override fun onDestroy() {
        super.onDestroy()
        navigator.activity = null
    }
}