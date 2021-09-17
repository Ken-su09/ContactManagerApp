package com.suonk.contactmanagerapp.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.suonk.contactmanagerapp.ui.fragments.main_pages.ContactsListFragment
import com.suonk.contactmanagerapp.ui.fragments.main_pages.FavoriteContactsFragment

class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                ContactsListFragment()
            }
            1 -> {
                FavoriteContactsFragment()
            }
            2 -> {
                ContactsListFragment()
            }
            else -> {
                ContactsListFragment()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> {
                "ContactsListFragment()"
            }
            1 -> {
                "FavoriteContactsFragment()"
            }
            2 -> {
                "ContactsListFragment()"
            }
            else -> {
                "ContactsListFragment()"
            }
        }
    }
}