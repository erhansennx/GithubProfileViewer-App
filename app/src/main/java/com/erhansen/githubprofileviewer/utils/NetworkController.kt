package com.erhansen.githubprofileviewer.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.LayoutInflater
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.erhansen.githubprofileviewer.R

object NetworkController {



    fun isNetworkAvailable(context:Context) : Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
    }

    fun showLayout(context:Context) {
        lateinit var alertDialog: AlertDialog
        val builder = AlertDialog.Builder(context)
        val view = LayoutInflater.from(context).inflate(R.layout.network_disconnected, null)
        val okayButton = view.findViewById<Button>(R.id.okayButton)
        builder.setView(view)
        alertDialog = builder.create()
        alertDialog.show()
        okayButton.setOnClickListener {
            alertDialog.dismiss()
        }
    }

}