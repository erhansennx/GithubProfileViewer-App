package com.erhansen.githubprofileviewer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.erhansen.githubprofileviewer.R
import com.erhansen.githubprofileviewer.adapter.FollowingAdapter
import com.erhansen.githubprofileviewer.databinding.FragmentFollowingBinding
import com.erhansen.githubprofileviewer.model.FollowersModel
import com.erhansen.githubprofileviewer.service.UserProfileApi
import com.erhansen.githubprofileviewer.service.UserProfileApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FollowingFragment : Fragment() {

    private lateinit var userName: String
    private lateinit var followingAdapter: FollowingAdapter
    private lateinit var followersList: ArrayList<FollowersModel>
    private lateinit var fragmentFollowingBinding: FragmentFollowingBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentFollowingBinding = FragmentFollowingBinding.inflate(layoutInflater)

        followersList = ArrayList()
        userName = arguments?.getString("username", null).toString()


        return fragmentFollowingBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(fragmentFollowingBinding) {

            val userProfileApi = UserProfileApiService.getInstance().create(UserProfileApi::class.java)
            val scope = CoroutineScope(Dispatchers.Main)

            CoroutineScope(Dispatchers.Main).launch {

                val followers = withContext(Dispatchers.IO) {
                    userProfileApi.getUserFollowing(userName)
                }

                if (followers.isSuccessful) {
                    progressBar.visibility = View.GONE
                    if (followers.body()!!.size > 0) {
                        followingAdapter = FollowingAdapter(requireContext(), followers.body()!!)
                        followersRecyclerView.adapter = followingAdapter
                    } else {
                        errorFollowingText.visibility = View.VISIBLE
                        errorFollowingText.text = "$userName ${getString(R.string.no_following)}"
                    }


                }

            }


        }


    }

}