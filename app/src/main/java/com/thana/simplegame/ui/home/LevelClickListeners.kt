package com.thana.simplegame.ui.home

interface LevelClickListeners {

    fun navigate(fragmentID: Int)

    fun isLevelLocked(level:Int):Boolean
}