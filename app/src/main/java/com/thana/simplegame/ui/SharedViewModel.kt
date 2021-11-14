package com.thana.simplegame.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thana.simplegame.data.repository.SharedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SharedViewModel @Inject constructor(private val sharedRepository: SharedRepository) :
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

    fun getScore() =  sharedRepository.getScore()

    fun deletePref(key: String) {
        viewModelScope.launch {
            sharedRepository.deletePref(key)
        }
    }
}