package com.thana.simplegame.ui.level2

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import com.thana.simplegame.R
import com.thana.simplegame.databinding.FragmentLevelTwoBinding
import com.thana.simplegame.ui.SharedViewModel
import com.thana.simplegame.ui.common.BaseFragment
import com.thana.simplegame.ui.common.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LevelTwoFragment : BaseFragment(R.layout.fragment_level_two) {

    private val binding by viewBinding(FragmentLevelTwoBinding::bind)
    private val viewModel: SharedViewModel by viewModels()
    private var isExpanded = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.next.setOnClickListener {
            nextLevel()
        }

        showHint()
        validateAnswer()

    }

    private fun showHint() {

        binding.hintRoot.setOnClickListener {
            if (isExpanded) expand() else collapse()
        }
        binding.expand.setOnClickListener {
            if (isExpanded) expand() else collapse()
        }
        binding.collapse.setOnClickListener {
            if (isExpanded) expand() else collapse()
        }
    }

    private fun expand() {

        binding.hint.visibility = View.VISIBLE
        binding.collapse.visibility = View.VISIBLE
        binding.expand.visibility = View.INVISIBLE
        isExpanded = false
    }

    private fun collapse() {
        binding.hint.visibility = View.GONE
        binding.collapse.visibility = View.INVISIBLE
        binding.expand.visibility = View.VISIBLE
        isExpanded = true

    }
    private fun nextLevel() {
        val action = LevelTwoFragmentDirections.actionLevelTwoFragmentToLevelThreeFragment()
        findNavController().navigate(action)
    }

    private fun validateAnswer() {

        val loseAudio = ExoPlayer.Builder(requireContext()).build()

        val loseUri = RawResourceDataSource.buildRawResourceUri(R.raw.lose)

        val winAudio = ExoPlayer.Builder(requireContext()).build()

        val winUri = RawResourceDataSource.buildRawResourceUri(R.raw.win)


        loseAudio.apply {
            setMediaItem(MediaItem.fromUri(loseUri))
            prepare()
        }

        winAudio.apply {
            setMediaItem(MediaItem.fromUri(winUri))
            prepare()
        }

        binding.slider.addOnChangeListener { slider, value, _ ->
            if (value < 10.0) {

                binding.right.visibility = View.VISIBLE
                binding.wrong.visibility = View.INVISIBLE
                binding.next.visibility = View.VISIBLE
                binding.celebrate.visibility = View.VISIBLE
                binding.celebrate.playAnimation()

                slider.value = 0.0f
                slider.isEnabled = false
                winAudio.play()

                if (viewModel.getScore() < 2) {
                    viewModel.addScore()
                }

            } else if (value > 50.0) {

                binding.right.visibility = View.GONE
                binding.wrong.visibility = View.VISIBLE
                loseAudio.play()
            }

        }
    }
}

