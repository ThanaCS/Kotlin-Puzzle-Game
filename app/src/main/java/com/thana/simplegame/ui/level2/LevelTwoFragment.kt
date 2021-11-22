package com.thana.simplegame.ui.level2

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.next.setOnClickListener {
            nextLevel()
        }
        setListeners()
        showHint()
        validateAnswer()

    }

    private fun setListeners() {
        binding.hintRoot.setOnClickListener {
            viewModel.isExpanded = !viewModel.isExpanded
            showHint()
        }
        binding.expand.setOnClickListener {
            viewModel.isExpanded = !viewModel.isExpanded
            showHint()
        }
    }

    private fun showHint() = if (viewModel.isExpanded) {
        binding.hint.visibility = View.VISIBLE
        binding.expand.setIconResource(R.drawable.ic_collapse_arrow)
    } else {
        binding.hint.visibility = View.GONE
        binding.expand.setIconResource(R.drawable.ic_expand_arrow)
    }

    private fun nextLevel() {
        val action = LevelTwoFragmentDirections.actionLevelTwoFragmentToLevelThreeFragment()
        findNavController().navigate(action)
    }

    private fun validateAnswer() {


        binding.slider.addOnChangeListener { slider, value, _ ->
            if (value < 10.0) {

                binding.right.visibility = View.VISIBLE
                binding.wrong.visibility = View.INVISIBLE
                binding.next.visibility = View.VISIBLE
                binding.celebrate.visibility = View.VISIBLE
                binding.celebrate.playAnimation()

                slider.value = 0.0f
                slider.isEnabled = false
                viewModel.playWin()

                viewModel.addScore(levelNumber = 2)

            } else if (value > 50.0) {

                binding.right.visibility = View.GONE
                binding.wrong.visibility = View.VISIBLE
                viewModel.playLose()
            }

        }
    }
}

