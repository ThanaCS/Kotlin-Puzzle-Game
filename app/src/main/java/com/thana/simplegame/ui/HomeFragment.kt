package com.thana.simplegame.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.thana.simplegame.R
import com.thana.simplegame.data.common.SharedPreferences
import com.thana.simplegame.data.model.levelList
import com.thana.simplegame.databinding.FragmentHomeBinding
import com.thana.simplegame.ui.common.BaseFragment
import com.thana.simplegame.ui.common.viewBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private lateinit var adapter: GameLevelsAdapter
    private val viewModel: SharedViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initAdapter()
        setScore()
        setGems()
    }

    private fun initAdapter() {

        binding.recyclerview.layoutManager = GridLayoutManager(requireActivity(), 3)

        adapter = GameLevelsAdapter(object : LevelClickListeners {
            override fun navigate(fragmentID: Int) {
                findNavController().navigate(fragmentID)
            }

            override fun isLevelLocked(level: Int): Boolean {
                return level > viewModel.getScore() + 1
            }

        })

        binding.recyclerview.adapter = adapter
        adapter.setItems(levelList)
    }

    private fun setScore() {
        binding.score.text = viewModel.getScore().toString()
    }

    private fun setGems() {
        binding.gems.text = (viewModel.getScore() * 10).toString()
    }

}
