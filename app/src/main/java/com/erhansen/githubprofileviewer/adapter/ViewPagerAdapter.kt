package com.erhansen.githubprofileviewer.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.erhansen.githubprofileviewer.fragments.FollowersFragment
import com.erhansen.githubprofileviewer.fragments.FollowingFragment
import com.erhansen.githubprofileviewer.fragments.RepositoriesFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity, private val username: String) :
    FragmentStateAdapter(fragmentActivity) {

    private var bundle = Bundle()

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {

        bundle.putString("username", username)

        when (position) {
            0 -> {
                val followersFragment = FollowersFragment()
                followersFragment.arguments = bundle
                return followersFragment
            }
            1 -> {
                val followingFragment = FollowingFragment()
                followingFragment.arguments = bundle
                return followingFragment
            }
            2 -> {
                val repositoriesFragment = RepositoriesFragment()
                repositoriesFragment.arguments = bundle
                return repositoriesFragment
            }
            else -> {
                val followersFragment = FollowersFragment()
                followersFragment.arguments = bundle
                return followersFragment
            }
        }

    }

}