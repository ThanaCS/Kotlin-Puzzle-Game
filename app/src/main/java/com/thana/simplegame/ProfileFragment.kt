package com.thana.simplegame

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.thana.simplegame.databinding.FragmentProfileBinding
import com.thana.simplegame.ui.SharedViewModel
import com.thana.simplegame.ui.common.BaseFragment
import com.thana.simplegame.ui.common.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)

    private val viewModel: SharedViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setProfileInfo()

    }

    private fun setProfileInfo() {
        val score = viewModel.getScore()
        val totalLevels = 25
        binding.progressBar.progress = score * 100 / totalLevels
        binding.gems.text = (score * 13).toString()
        binding.score.text = score.toString()
    }
}

