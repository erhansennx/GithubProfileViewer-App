package com.erhansen.githubprofileviewer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.erhansen.githubprofileviewer.R
import com.erhansen.githubprofileviewer.adapter.FollowersAdapter
import com.erhansen.githubprofileviewer.databinding.FragmentFollowersBinding
import com.erhansen.githubprofileviewer.model.FollowersModel
import com.erhansen.githubprofileviewer.service.UserProfileApi
import com.erhansen.githubprofileviewer.service.UserProfileApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FollowersFragment : Fragment() {

    private lateinit var userName: String
    private lateinit var followersAdapter: FollowersAdapter
    private lateinit var followersList: ArrayList<FollowersModel>
    private lateinit var fragmentFollowersBinding: FragmentFollowersBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentFollowersBinding = FragmentFollowersBinding.inflate(layoutInflater)

        followersList = ArrayList()
        userName = arguments?.getString("username", null).toString()

        return fragmentFollowersBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(fragmentFollowersBinding) {

            val userProfileApi = UserProfileApiService.getInstance().create(UserProfileApi::class.java)
            val scope = CoroutineScope(Dispatchers.Main)

            CoroutineScope(Dispatchers.Main).launch {

                val followers = withContext(Dispatchers.IO) {
                    userProfileApi.getUserFollowers(userName)
                }

                if (followers.isSuccessful) {
                    progressBar.visibility = View.GONE
                    if (followers.body()!!.size > 0) {
                        followersAdapter = FollowersAdapter(requireContext(), followers.body()!!)
                        followersRecyclerView.adapter = followersAdapter
                    } else {
                        errorFollowersText.visibility = View.VISIBLE
                        errorFollowersText.text = "$userName ${getString(R.string.no_followers)}"
                    }

                }

            }


        }


    }

}