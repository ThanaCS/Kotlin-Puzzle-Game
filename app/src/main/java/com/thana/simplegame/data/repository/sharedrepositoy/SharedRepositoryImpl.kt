package com.thana.simplegame.data.repository.sharedrepositoy

import com.thana.simplegame.data.common.SharedPreferences
import com.thana.simplegame.data.repository.sharedrepositoy.SharedRepository
import javax.inject.Inject

class SharedRepositoryImpl @Inject constructor(private val sharedPreferences: SharedPreferences) :
    SharedRepository {

    override fun addScore() {
        sharedPreferences.addScore()
    }

    override fun setScore(value: Int) {
        sharedPreferences.setScore(value)
    }

    override fun getScore() = sharedPreferences.getScore()

    override fun deletePref(key: String) {
        sharedPreferences.deletePref(key)
    }

}