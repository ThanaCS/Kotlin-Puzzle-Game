package com.thana.simplegame.data.common

import android.content.Context
import android.content.SharedPreferences

class SharedPreferences constructor(context: Context) {

    var sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)

    fun addScore() {
        setScore(getScore() + 1)
    }

    fun setScore(value: Int) {
        sharedPreferences.edit().putInt(SCORE, value).apply()
    }

    fun getScore(default: Int = 0): Int {
        return sharedPreferences.getInt(SCORE, default)
    }


    fun deletePref(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }


    companion object {
        private const val SCORE = "score"
        private const val PREFERENCES = "My_PREFERENCES"
    }
}