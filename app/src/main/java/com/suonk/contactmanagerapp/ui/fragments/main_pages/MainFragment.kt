package com.suonk.contactmanagerapp.ui.fragments.main_pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.suonk.contactmanagerapp.R
import com.suonk.contactmanagerapp.databinding.FragmentMainBinding
import com.suonk.contactmanagerapp.ui.activity.MainActivity
import com.suonk.contactmanagerapp.ui.adapters.ViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var binding: FragmentMainBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        initializeUI()
        return binding!!.root
    }

    private fun initializeUI() {
        setupViewPager()
    }

    private fun setupViewPager() {
        binding!!.viewPager.adapter = ViewPagerAdapter(activity as MainActivity)

        TabLayoutMediator(
            binding!!.tabLayout,
            binding!!.viewPager
        ) { tab, position ->
            when (position) {
                0 -> {
                    tab.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_user, null)
                }
                1 -> {
                    tab.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_star, null)
                }
                2 -> {
                    tab.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_group, null)
                }
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}