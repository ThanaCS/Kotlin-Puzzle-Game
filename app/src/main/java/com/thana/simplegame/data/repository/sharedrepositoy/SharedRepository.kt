package com.thana.simplegame.data.repository.sharedrepositoy

interface SharedRepository {

    fun addScore()

    fun setScore(value: Int)

    fun getScore(): Int

    fun deletePref(key: String)
}