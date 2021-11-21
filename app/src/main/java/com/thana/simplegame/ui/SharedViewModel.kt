package com.thana.simplegame.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thana.simplegame.data.repository.sharedrepositoy.SharedRepository
import com.thana.simplegame.data.repository.soundrepository.SoundRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SharedViewModel @Inject constructor(
    private val sharedRepository: SharedRepository,
    private val soundRepository: SoundRepository
) :
    ViewModel() {

    fun addScore() {
        viewModelScope.launch {
            sharedRepository.addScore()
        }
    }

    fun setScore(value: Int) {
        viewModelScope.launch {
            sharedRepository.setScore(value)
        }
    }

    fun getScore() = sharedRepository.getScore()

    fun deletePref(key: String) {
        viewModelScope.launch {
            sharedRepository.deletePref(key)
        }
    }

    fun playWin() {
        soundRepository.playWin()
    }

    fun playLose() {
        soundRepository.playLose()
    }


}