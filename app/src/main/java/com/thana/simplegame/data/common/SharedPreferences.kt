package com.thana.simplegame.data.common

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferences @Inject constructor(private val application: Application) {

    var sharedPreferences: SharedPreferences =
        application.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)

    fun addScore() {
        setScore(getScore() + 1)
    }

    fun setScore(value: Int) {
        sharedPreferences.edit().putInt(SCORE, value).apply()
    }

    fun getScore(default: Int = 0) = sharedPreferences.getInt(SCORE, default)



    fun deletePref(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }


    companion object {
        private const val SCORE = "score"
        private const val PREFERENCES = "My_PREFERENCES"
    }
}