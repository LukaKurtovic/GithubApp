package com.example.githubapp.helpers

import android.content.Context
import android.content.SharedPreferences

class SharedPrefs(context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = preferences.edit()

    fun addLastSearchQuery(query: String) {
        editor.apply {
            putString(QUERY, query)
        }.commit()
    }

    fun getLastSearchQuery(): String = preferences.getString(QUERY, "") ?: ""

}

private const val PREFERENCES = "preferences"
private const val QUERY = "query"