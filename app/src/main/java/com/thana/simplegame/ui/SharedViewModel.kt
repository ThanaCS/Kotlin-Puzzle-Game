package com.thana.simplegame.ui

import androidx.lifecycle.ViewModel
import com.thana.simplegame.data.repository.sharedrepositoy.SharedRepository
import com.thana.simplegame.data.repository.soundrepository.SoundRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

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

}