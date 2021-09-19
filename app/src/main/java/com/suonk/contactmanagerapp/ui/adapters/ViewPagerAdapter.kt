package com.suonk.contactmanagerapp.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.suonk.contactmanagerapp.ui.fragments.main_pages.ContactsListFragment
import com.suonk.contactmanagerapp.ui.fragments.main_pages.FavoriteContactsFragment
import com.suonk.contactmanagerapp.ui.fragments.main_pages.MessagesListFragment

class ViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                ContactsListFragment()
            }
            1 -> {
                FavoriteContactsFragment()
            }
            2 -> {
                MessagesListFragment()
            }
            else -> {
                ContactsListFragment()
            }
        }
    }
}