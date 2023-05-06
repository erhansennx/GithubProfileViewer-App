package com.erhansen.githubprofileviewer.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.erhansen.githubprofileviewer.R
import com.erhansen.githubprofileviewer.databinding.FragmentSearchUserBinding
import com.erhansen.githubprofileviewer.model.UserProfileModel
import com.erhansen.githubprofileviewer.service.UserProfileApi
import com.erhansen.githubprofileviewer.service.UserProfileApiService
import com.erhansen.githubprofileviewer.utils.CreateFragment
import kotlinx.coroutines.*
import retrofit2.Response

class SearchUserFragment : Fragment() {

    private var name: String ?= null
    private var username: String ?= null
    private var avatarURL: String ?= null
    private lateinit var fragmentSearchUserBinding: FragmentSearchUserBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentSearchUserBinding = FragmentSearchUserBinding.inflate(layoutInflater)

        fragmentSearchUserBinding.userProgress.visibility = View.GONE
        fragmentSearchUserBinding.userProfileLayout.visibility = View.GONE
        fragmentSearchUserBinding.userNotFound.visibility = View.GONE

        return fragmentSearchUserBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userProfileApi = UserProfileApiService.getInstance().create(UserProfileApi::class.java)
        val scope = CoroutineScope(Dispatchers.Main)

        with(fragmentSearchUserBinding) {
            searchUser.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (!searchUser.text.isNullOrEmpty()) {
                        userProgress.visibility = View.VISIBLE
                        searchUser.clearFocus()
                        searchUserLayout.boxBackgroundColor = ContextCompat.getColor(requireActivity(),
                            R.color.transparent
                        )
                        CoroutineScope(Dispatchers.Main).launch {

                            val result = withContext(Dispatchers.IO) {
                                userProfileApi.getUserInformation(searchUser.text.toString())
                            }

                            if (result.isSuccessful) {
                                userProfileLayout.visibility = View.VISIBLE
                                userNotFound.visibility = View.GONE
                                showRepoProgress.visibility = View.GONE
                                nullViewCheck(result)
                                name = result.body()!!.name
                                username = result.body()!!.login
                                avatarURL = result.body()!!.avatarUrl
                                Glide.with(requireActivity()).load(avatarURL).centerCrop().placeholder(
                                    R.drawable.ic_launcher_background
                                ).into(profilePhoto)
                                nameText.text = name
                                usernameText.text = username
                                biographyText.text = result.body()?.bio
                                followerText.text = "${result.body()!!.followers} followers · ${result.body()!!.following} following"
                                locationText.text = result.body()?.location
                                emailText.text = result.body()?.email.toString()
                                companyText.text = result.body()?.company.toString()
                                twitterText.text = result.body()?.twitterUsername.toString()
                                linkText.text = result.body()?.blog
                                userProgress.visibility = View.GONE
                            } else if (result.code() == 404) {
                                // user not found!
                                userProfileLayout.visibility = View.GONE
                                userNotFound.visibility = View.VISIBLE
                                userNotFoundText.text = "${getString(R.string.user_not_found)} '${searchUser.text}'"
                            }
                            userProgress.visibility = View.GONE
                            searchUserLayout.boxBackgroundColor = ContextCompat.getColor(requireActivity(),
                                R.color.white
                            )
                        }
                        //runBlocking {//globalscope.launch {} exception :  Only the original thread that created a view hierarchy can touch its views. }
                    }
                    val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(searchUser.windowToken, 0)
                    return@setOnEditorActionListener true
                } else {
                    return@setOnEditorActionListener false
                }
            }

            scope.cancel()

            showRepositories.setOnClickListener {
                showRepoProgress.visibility = View.VISIBLE
                CoroutineScope(Dispatchers.Main).launch { // runBlocking {} made a delay?
                    val getRepositories =  withContext(Dispatchers.IO) {
                        userProfileApi.getUserRepositories(searchUser.text.toString())
                    }
                    if (getRepositories.isSuccessful) {
                        val bundle = Bundle()
                        bundle.putParcelable("repositories", getRepositories.body())
                        bundle.putString("name", name)
                        bundle.putString("username", username)
                        bundle.putString("avatarURL", avatarURL)
                        val createFragment = CreateFragment(requireActivity() as AppCompatActivity, RepositoriesFragment(), bundle)
                        //searchUser.text = null
                    }
                    showRepoProgress.visibility = View.VISIBLE
                }
            }

        }

    }

    private fun nullViewCheck(result: Response<UserProfileModel>) {
        with(fragmentSearchUserBinding) {
            if (result.body()?.name.isNullOrEmpty()) nameText.visibility = android.view.View.GONE else nameText.visibility = android.view.View.VISIBLE
            if (result.body()?.name.isNullOrEmpty()) nameText.visibility = android.view.View.GONE else nameText.visibility = android.view.View.VISIBLE
            if (result.body()?.bio.isNullOrEmpty()) biographyText.visibility = android.view.View.GONE else biographyText.visibility = android.view.View.VISIBLE
            if (result.body()?.location.isNullOrEmpty()) locationText.visibility = android.view.View.GONE else locationText.visibility = android.view.View.VISIBLE
            if (result.body()?.email.toString() == "null") emailText.visibility = android.view.View.GONE else emailText.visibility = android.view.View.VISIBLE
            if (result.body()?.company.toString() == "null") companyText.visibility = android.view.View.GONE else companyText.visibility = android.view.View.VISIBLE
            if (result.body()?.twitterUsername.toString() == "null") twitterText.visibility = android.view.View.GONE else twitterText.visibility = android.view.View.VISIBLE
            if (result.body()?.blog.isNullOrEmpty()) linkText.visibility = android.view.View.GONE else linkText.visibility = android.view.View.VISIBLE
        }
    }

}