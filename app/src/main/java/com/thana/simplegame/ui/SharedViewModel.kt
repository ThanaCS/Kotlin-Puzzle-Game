package com.thana.simplegame.ui

import androidx.lifecycle.ViewModel
import com.thana.simplegame.data.repository.sharedrepositoy.SharedRepository
import com.thana.simplegame.data.repository.soundrepository.SoundRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.thana.simplegame.data.model.levelList

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val sharedRepository: SharedRepository,
    private val soundRepository: SoundRepository
) : ViewModel() {

    var isExpanded = false

    fun getScore() = sharedRepository.getScore()

    fun addScore(levelNumber: Int) =
        if (sharedRepository.getScore() < levelNumber) sharedRepository.addScore() else Unit

    fun playWin() {
        soundRepository.playWin()
    }

    fun playLose() {
        soundRepository.playLose()
    }

    fun isLevelLocked(level: Int) = level > getScore() + 1

    fun getCoins() = getScore() / 2

    fun getGems() = getScore() * 13

    fun getProgress() = getScore() * 100 / levelList.last().level
}