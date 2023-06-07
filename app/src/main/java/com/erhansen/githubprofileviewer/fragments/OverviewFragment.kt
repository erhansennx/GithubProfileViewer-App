package com.erhansen.githubprofileviewer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.erhansen.githubprofileviewer.R
import com.erhansen.githubprofileviewer.adapter.RepositoriesAdapter
import com.erhansen.githubprofileviewer.adapter.ViewPagerAdapter
import com.erhansen.githubprofileviewer.databinding.FragmentOverviewBinding
import com.erhansen.githubprofileviewer.model.RepositoriesModel
import com.erhansen.githubprofileviewer.utils.CreateFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

class OverviewFragment : Fragment() {

    private var name: String ?= null
    private var username: String ?= null
    private var avatarURL: String ?= null
    private var biography: String ?= null
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var fragmentOverviewBinding: FragmentOverviewBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentOverviewBinding = FragmentOverviewBinding.inflate(layoutInflater)

        name = arguments?.getString("name",null)
        username = arguments?.getString("username",null)
        avatarURL = arguments?.getString("avatarURL",null)
        biography = arguments?.getString("biography", null)
        viewPagerAdapter = ViewPagerAdapter(requireActivity(), username.toString())
        Glide.with(requireActivity()).load(avatarURL).centerCrop().placeholder(R.drawable.glide_loading).into(fragmentOverviewBinding.profilePhoto)
        if (name.isNullOrEmpty()) fragmentOverviewBinding.nameText.visibility = View.GONE
        else fragmentOverviewBinding.nameText.text = name
        fragmentOverviewBinding.usernameText.text = username
        fragmentOverviewBinding.biographyText.text = biography

        return fragmentOverviewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(fragmentOverviewBinding) {
            //repoRecycler.adapter = repositoriesAdapter
            backButton.setOnClickListener {
                //val createFragment = CreateFragment(requireActivity() as AppCompatActivity, SearchUserFragment(), null)
                CreateFragment.create(requireActivity() as AppCompatActivity, SearchUserFragment(), null)
            }

            viewPager.adapter = viewPagerAdapter
            tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    viewPager.currentItem = tab?.position!!
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) { }

                override fun onTabReselected(tab: TabLayout.Tab?) { }

            })
            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    tabLayout.getTabAt(position)?.select()
                }
            })

        }

    }


}