package com.thana.simplegame

import android.content.Intent
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
        binding.share.setOnClickListener { shareProgress() }
    }

    private fun setProfileInfo() {
        val score = viewModel.getScore()
        val coins = score / 2
        val totalLevels = 25
        binding.progressBar.progress = score * 100 / totalLevels
        binding.gems.text = (score * 13).toString()
        binding.levelUnlockedNumber.text = score.toString()
        binding.score.text = coins.toString()
    }

    private fun shareProgress() {
        try {
            val score = viewModel.getScore()
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            var shareMessage = getString(R.string.share_text, score)
            shareMessage =
                """
                ${shareMessage}https://play.google.com/store/apps/details?id=
                ${BuildConfig.APPLICATION_ID}
                """.trimIndent()
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            startActivity(Intent.createChooser(shareIntent, null))
        } catch (e: Exception) {

        }
    }
}

