package com.pitchedapps.frost.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by Allan Wang on 2017-05-28.
 *
 * Shared Preference object with lazy cached retrievals
 */

private val PREFERENCE_NAME = "${com.pitchedapps.frost.BuildConfig.APPLICATION_ID}.prefs"
private val LAST_ACTIVE = "last_active"
private val USER_ID = "user_id"

object Prefs {

    private const val prefDefaultLong = -2L

    lateinit private var c: Context
    operator fun invoke(c: Context) {
        this.c = c
        lastActive = 0
    }

    private val sp: SharedPreferences by lazy { c.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE) }

    var lastActive: Long = prefDefaultLong
        get() {
            if (field == prefDefaultLong) field = sp.getLong(LAST_ACTIVE, -1)
            return field
        }
        set(value) {
            field = value
            if (value != prefDefaultLong) set(LAST_ACTIVE, System.currentTimeMillis())
        }

    const val userIdDefault = -1L
    var userId: Long = prefDefaultLong
        get() {
            if (field == prefDefaultLong) field = sp.getLong(USER_ID, userIdDefault)
            return field
        }
        set(value) {
            field = value
            if (value != prefDefaultLong) set(USER_ID, value)
        }

    private fun set(key: String, value: Boolean) = sp.edit().putBoolean(key, value).apply()
    private fun set(key: String, value: Int) = sp.edit().putInt(key, value).apply()
    private fun set(key: String, value: Long) = sp.edit().putLong(key, value).apply()
    private fun set(key: String, value: String) = sp.edit().putString(key, value).apply()

    fun clear() {
        L.d("Clearing Prefs")
        sp.edit().clear().apply()
        lastActive = prefDefaultLong
        userId = prefDefaultLong
    }
}