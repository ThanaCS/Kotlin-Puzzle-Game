package com.thana.simplegame.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.thana.simplegame.R
import com.thana.simplegame.databinding.FragmentLevelTwoBinding
import com.thana.simplegame.ui.common.BaseFragment
import com.thana.simplegame.ui.common.viewBinding

class LevelTwoFragment : BaseFragment(R.layout.fragment_level_two) {

    private val binding by viewBinding(FragmentLevelTwoBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()

        binding.next.setOnClickListener {
            nextLevel()
        }
    }

    private fun nextLevel() {
        val action = LevelTwoFragmentDirections.actionLevelTwoFragmentToLevelThreeFragment()
        findNavController().navigate(action)
    }


    private fun setListeners() {

        binding.slider.addOnChangeListener { slider, value, _ ->

            if (value < 10.0) {

                binding.right.visibility = View.VISIBLE
                binding.wrong.visibility = View.GONE

                slider.value = 0.0f
                slider.isEnabled = false

            } else if (value > 50.0) {

                binding.right.visibility = View.GONE
                binding.wrong.visibility = View.VISIBLE

            }

        }
    }
}
