package com.suonk.contactmanagerapp.ui.fragments.main_pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.suonk.contactmanagerapp.databinding.FragmentGroupsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupsFragment : Fragment() {

    private var binding: FragmentGroupsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGroupsBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}