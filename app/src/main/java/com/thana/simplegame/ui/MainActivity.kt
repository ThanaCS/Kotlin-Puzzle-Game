package com.thana.simplegame.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.thana.simplegame.ui.common.viewBinding
import com.thana.simplegame.R
import com.thana.simplegame.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.fab.setOnClickListener {
            navController.navigate(R.id.homeFragment)
        }
        binding.bottomNavigationView.background = null


        binding.bottomNavigationView.menu.getItem(2).isEnabled = false

        binding.bottomNavigationView.menu.getItem(1).isEnabled = false

        navController = findNavController(R.id.nav_host_fragment)

        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}