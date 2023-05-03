package com.erhansen.githubprofileviewer.utils

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

object ColorReader {

    fun getColorCode(context:Context, language: String?): String {
        val json = context.assets.open("language_colors.json").bufferedReader().use { it.readText() }
        if (language != null) {
            val jsonObject = JSONObject(json)
            return jsonObject.optString(language)
        }
        return "#4CAF50"
    }

}