package com.erhansen.githubprofileviewer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.erhansen.githubprofileviewer.R
import com.erhansen.githubprofileviewer.adapter.RepositoriesAdapter
import com.erhansen.githubprofileviewer.databinding.FragmentRepositoriesBinding
import com.erhansen.githubprofileviewer.model.RepositoriesModel
import com.erhansen.githubprofileviewer.utils.CreateFragment

class RepositoriesFragment : Fragment() {

    private var name: String ?= null
    private var username: String ?= null
    private var avatarURL: String ?= null
    private lateinit var repositories: RepositoriesModel
    private lateinit var repositoriesAdapter: RepositoriesAdapter
    private lateinit var fragmentRepositoriesBinding: FragmentRepositoriesBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentRepositoriesBinding = FragmentRepositoriesBinding.inflate(layoutInflater)

        repositories = arguments?.getParcelable<RepositoriesModel>("repositories") ?: RepositoriesModel()
        name = arguments?.getString("name",null)
        username = arguments?.getString("username",null)
        avatarURL = arguments?.getString("avatarURL",null)
        fragmentRepositoriesBinding.repoHeader.text = "${getString(R.string.repositories)}"
        fragmentRepositoriesBinding.repoCount.text = "${repositories.size}"
        Glide.with(requireActivity()).load(avatarURL).centerCrop().placeholder(R.drawable.ic_launcher_background).into(fragmentRepositoriesBinding.profilePhoto)
        fragmentRepositoriesBinding.nameText.text = name
        fragmentRepositoriesBinding.usernameText.text = username
        repositoriesAdapter = RepositoriesAdapter(requireContext(), repositories)

        return fragmentRepositoriesBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(fragmentRepositoriesBinding) {
            repoRecycler.adapter = repositoriesAdapter
            backButton.setOnClickListener {
                val createFragment = CreateFragment(requireActivity() as AppCompatActivity, SearchUserFragment(), null)
            }
        }

    }


}