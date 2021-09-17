package com.suonk.contactmanagerapp.ui.fragments.main_pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.suonk.contactmanagerapp.databinding.FragmentGroupsBinding
import com.suonk.contactmanagerapp.databinding.FragmentMainBinding
import com.suonk.contactmanagerapp.ui.activity.MainActivity
import com.suonk.contactmanagerapp.ui.adapters.ViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var binding: FragmentMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        setupViewPager()
        return binding!!.root
    }

    private fun setupViewPager() {
        binding!!.viewPager.adapter =
            ViewPagerAdapter((activity as MainActivity).supportFragmentManager)
        binding!!.tabLayout.setupWithViewPager(binding!!.viewPager)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}