package com.erhansen.githubprofileviewer.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.erhansen.githubprofileviewer.R
import com.erhansen.githubprofileviewer.adapter.FollowingAdapter
import com.erhansen.githubprofileviewer.adapter.RepositoriesAdapter
import com.erhansen.githubprofileviewer.databinding.FragmentRepositoriesBinding
import com.erhansen.githubprofileviewer.model.RepositoriesModel
import com.erhansen.githubprofileviewer.service.UserProfileApi
import com.erhansen.githubprofileviewer.service.UserProfileApiService
import com.erhansen.githubprofileviewer.utils.NetworkController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RepositoriesFragment : Fragment() {

    private lateinit var username: String
    private lateinit var repositoriesAdapter: RepositoriesAdapter
    private lateinit var fragmentRepositoriesBinding: FragmentRepositoriesBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentRepositoriesBinding = FragmentRepositoriesBinding.inflate(layoutInflater)

        username = arguments?.getString("username", null).toString()

        return fragmentRepositoriesBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(fragmentRepositoriesBinding) {

            if (NetworkController.isNetworkAvailable(requireContext())) {
                val userProfileApi = UserProfileApiService.getInstance().create(UserProfileApi::class.java)

                CoroutineScope(Dispatchers.Main).launch {

                    val repositories = withContext(Dispatchers.IO) {
                        userProfileApi.getUserRepositories(username)
                    }

                    if (repositories.isSuccessful) {
                        repoProgressBar.visibility = View.GONE
                        if (repositories.body()!!.size > 0) {
                            repositoriesAdapter = RepositoriesAdapter(requireContext(), repositories.body()!!)
                            repositoriesRecyclerView.adapter = repositoriesAdapter
                        } else {
                            errorRepositoriesText.visibility = View.VISIBLE
                            errorRepositoriesText.text = "$username ${getString(R.string.no_repositories)}"
                        }
                    }

                }

            } else {
                NetworkController.showLayout(requireContext())
            }

        }

    }


}