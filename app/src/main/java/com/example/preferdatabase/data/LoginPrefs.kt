package com.example.preferdatabase.data


import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object LoginPrefs {

    private const val PREF_NAME = "login_prefs"
    private const val KEY_EMAIL = "email"
    private const val KEY_TOKEN = "token"

    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun save(email: String, token: String) = prefs.edit {
        putString(KEY_EMAIL, email)
        putString(KEY_TOKEN, token)
    }

    fun clear() = prefs.edit { clear() }

    val isLoggedIn: Boolean get() = prefs.contains(KEY_TOKEN)
    val email: String? get() = prefs.getString(KEY_EMAIL, null)
}
