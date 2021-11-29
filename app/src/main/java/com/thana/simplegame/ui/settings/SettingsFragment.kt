package com.thana.simplegame.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.thana.simplegame.BuildConfig
import com.thana.simplegame.R
import com.thana.simplegame.databinding.FragmentSettingsBinding
import com.thana.simplegame.ui.common.BaseFragment
import com.thana.simplegame.ui.common.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    private val binding by viewBinding(FragmentSettingsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()

    }

    private fun setListeners() {
        binding.rate.setOnClickListener { rateApp() }
        binding.share.setOnClickListener { share() }
        binding.contact.setOnClickListener { contact() }
        binding.help.setOnClickListener { help() }
    }

    private fun share() {
        try {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                val shareMessage =
                    "https://play.google.com/store/apps/details?id=" +
                            BuildConfig.APPLICATION_ID
                putExtra(Intent.EXTRA_TEXT, shareMessage)
            }
            startActivity(Intent.createChooser(intent, null))

        } catch (e: Exception) {

        }
    }

    private fun contact() {
        try {
            val intent = Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_EMAIL, arrayOf("Thana_CS@hotmail.com"))
                putExtra(Intent.EXTRA_SUBJECT, "Contact Developer")
                type = "message/rfc822"
            }

            startActivity(Intent.createChooser(intent, "Contact Developer"))
        } catch (e: Exception) {

        }
    }

    private fun help() {
        try {
            val intent = Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_EMAIL, arrayOf("Thana_CS@hotmail.com"))
                putExtra(Intent.EXTRA_SUBJECT, "Help/Suggestion")
                type = "message/rfc822"
            }

            startActivity(Intent.createChooser(intent, "Help/Suggestion"))
        } catch (e: Exception) {

        }
    }

    private fun rateApp() {
        val uri = Uri.parse("market://details?id=${BuildConfig.APPLICATION_ID}")
        val myAppLinkToMarket = Intent(Intent.ACTION_VIEW, uri)
        startActivity(myAppLinkToMarket)

    }
}