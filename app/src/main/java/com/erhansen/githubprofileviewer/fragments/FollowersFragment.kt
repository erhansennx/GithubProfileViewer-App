package com.erhansen.githubprofileviewer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.erhansen.githubprofileviewer.databinding.FragmentFollowersBinding

class FollowersFragment : Fragment() {

    private lateinit var fragmentFollowersBinding: FragmentFollowersBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentFollowersBinding = FragmentFollowersBinding.inflate(layoutInflater)



        return fragmentFollowersBinding.root
    }

}