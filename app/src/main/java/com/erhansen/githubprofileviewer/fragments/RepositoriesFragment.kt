package com.erhansen.githubprofileviewer.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.erhansen.githubprofileviewer.adapter.RepositoriesAdapter
import com.erhansen.githubprofileviewer.databinding.FragmentRepositoriesBinding
import com.erhansen.githubprofileviewer.model.RepositoriesModel

class RepositoriesFragment : Fragment() {

    private lateinit var repositories: RepositoriesModel
    private lateinit var repositoriesAdapter: RepositoriesAdapter
    private lateinit var fragmentRepositoriesBinding: FragmentRepositoriesBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentRepositoriesBinding = FragmentRepositoriesBinding.inflate(layoutInflater)

        repositories = arguments?.getParcelable<RepositoriesModel>("repositories") ?: RepositoriesModel()
        repositoriesAdapter = RepositoriesAdapter(requireContext(), repositories)
        //fragmentRepositoriesBinding.repositoriesRecycler.adapter = repositoriesAdapter


        return fragmentRepositoriesBinding.root
    }


}