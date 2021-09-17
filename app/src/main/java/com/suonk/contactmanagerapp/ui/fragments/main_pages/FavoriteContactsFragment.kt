package com.suonk.contactmanagerapp.ui.fragments.main_pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.suonk.contactmanagerapp.R
import com.suonk.contactmanagerapp.databinding.FragmentFavoriteContactsBinding
import com.suonk.contactmanagerapp.databinding.FragmentGroupsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteContactsFragment : Fragment() {

    private var binding: FragmentFavoriteContactsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteContactsBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}