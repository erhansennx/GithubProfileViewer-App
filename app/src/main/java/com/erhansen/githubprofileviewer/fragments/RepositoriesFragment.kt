package com.erhansen.githubprofileviewer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.erhansen.githubprofileviewer.databinding.FragmentRepositoriesBinding

class RepositoriesFragment : Fragment() {


    private lateinit var fragmentRepositoriesBinding: FragmentRepositoriesBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentRepositoriesBinding = FragmentRepositoriesBinding.inflate(layoutInflater)

        return fragmentRepositoriesBinding.root
    }


}