package com.thana.simplegame.ui

import android.os.Bundle
import android.view.View
import com.thana.simplegame.R
import com.thana.simplegame.databinding.FragmentLevelThreeBinding
import com.thana.simplegame.ui.common.BaseFragment
import com.thana.simplegame.ui.common.viewBinding


class LevelThreeFragment : BaseFragment(R.layout.fragment_level_three) {

    private val binding by viewBinding(FragmentLevelThreeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()

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

