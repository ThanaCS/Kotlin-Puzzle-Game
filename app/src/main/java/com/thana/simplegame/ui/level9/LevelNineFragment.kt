package com.thana.simplegame.ui.level9

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import com.thana.simplegame.R
import com.thana.simplegame.databinding.FragmentLevelNineBinding
import com.thana.simplegame.ui.SharedViewModel
import com.thana.simplegame.ui.common.BaseFragment
import com.thana.simplegame.ui.common.viewBinding
import com.thana.simplegame.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LevelNineFragment : BaseFragment(R.layout.fragment_level_nine){

    private val binding by viewBinding(FragmentLevelNineBinding::bind)
    private var isExpanded = true
    private lateinit var winAudio: ExoPlayer
    private lateinit var loseAudio: ExoPlayer
    private val viewModel: SharedViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


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

    private fun initAudio() {

        winAudio = ExoPlayer.Builder(requireContext()).build()
        loseAudio = ExoPlayer.Builder(requireContext()).build()

        val winUri = RawResourceDataSource.buildRawResourceUri(R.raw.win)
        val loseUri = RawResourceDataSource.buildRawResourceUri(R.raw.lose)

        winAudio.apply {
            setMediaItem(MediaItem.fromUri(winUri))
            prepare()

        }

        loseAudio.apply {
            setMediaItem(MediaItem.fromUri(loseUri))
            prepare()
        }
    }


    private fun validateAnswer() {

        binding.submit.setOnClickListener {
            initAudio()
            val input = binding.editText.text.toString().trim()
            if (input == "0") {
                binding.right.visibility = View.VISIBLE
                binding.wrong.visibility = View.GONE
                binding.celebrate.visibility = View.VISIBLE
                binding.celebrate.playAnimation()
                binding.submit.isEnabled = false
                binding.next.visibility = View.VISIBLE

                if (::winAudio.isInitialized)
                    winAudio.play()

                if (viewModel.getScore() < 9) {
                    viewModel.addScore()
                }

            } else {
                binding.wrong.visibility = View.VISIBLE
                binding.right.visibility = View.GONE

                if (::loseAudio.isInitialized)
                    loseAudio.play()
            }
            hideKeyboard()
        }
    }


}
