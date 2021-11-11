package com.thana.simplegame.ui

interface LevelClickListeners {

    fun navigate(fragmentID: Int)

    fun isLevelLocked(level:Int):Boolean
}