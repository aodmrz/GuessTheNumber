package com.aodmrz.guessthenumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.aodmrz.guessthenumber.databinding.ActivityMainBinding

const val PREFS_FILENAME = "com.aodmrz.guessthenumber"
const val EDITED_NAME = "edited_name_key"
const val HIGH_SCORE = "high_score_key"


class MainActivity : AppCompatActivity() {

    private lateinit var binding :ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //binding of BottomNavigation
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navHostFragment.navController)
        


    }
}