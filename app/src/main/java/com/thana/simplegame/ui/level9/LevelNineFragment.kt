package com.thana.simplegame.ui.level9

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.thana.simplegame.R
import com.thana.simplegame.databinding.FragmentLevelNineBinding
import com.thana.simplegame.ui.SharedViewModel
import com.thana.simplegame.ui.common.BaseFragment
import com.thana.simplegame.ui.common.viewBinding
import com.thana.simplegame.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LevelNineFragment : BaseFragment(R.layout.fragment_level_nine) {

    private val binding by viewBinding(FragmentLevelNineBinding::bind)
    private val viewModel: SharedViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showHint()
        validateAnswer()
        setListeners()

        binding.next.setOnClickListener {
            nextLevel()
        }
    }

    private fun showHint() = if (viewModel.isExpanded) {
        binding.hint.visibility = View.VISIBLE
        binding.expand.setIconResource(R.drawable.ic_collapse_arrow)
    } else {
        binding.hint.visibility = View.GONE
        binding.expand.setIconResource(R.drawable.ic_expand_arrow)
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

    private fun nextLevel() {
        val action = LevelNineFragmentDirections.actionLevelNineFragmentToLevelTenFragment()
        findNavController().navigate(action)
    }

    private fun validateAnswer() {

        binding.submit.setOnClickListener {
            val input = binding.editText.text.toString().trim()
            if (input == "0") {
                binding.right.visibility = View.VISIBLE
                binding.wrong.visibility = View.GONE
                binding.celebrate.visibility = View.VISIBLE
                binding.celebrate.playAnimation()
                binding.submit.isEnabled = false
                binding.next.visibility = View.VISIBLE

                viewModel.playWin()

                viewModel.addScore(levelNumber = 9)

            } else {
                binding.wrong.visibility = View.VISIBLE
                binding.right.visibility = View.GONE
                viewModel.playLose()
            }
            hideKeyboard()
        }
    }
}
