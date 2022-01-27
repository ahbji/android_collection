package com.codingnight.example.navigationdrawer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.codingnight.example.navigationdrawer.databinding.ActivityMainBinding
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var _toolbarLayout: CollapsingToolbarLayout
    val toolbarLayout: CollapsingToolbarLayout
        get() = _toolbarLayout

    private lateinit var _toolbarIconImageView: ImageView
    val toolbarIconImageView: ImageView
        get() = _toolbarIconImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.contentLayout.toolbar)

        navController = findNavController(R.id.fragment)

        _toolbarLayout = binding.contentLayout.toolbarLayout
        _toolbarIconImageView = binding.contentLayout.toolbarIconImageView

        appBarConfiguration =
            AppBarConfiguration(
                setOf(
                    R.id.firstFragment,
                    R.id.secondFragment,
                    R.id.thirdFragment
                ), findViewById(R.id.drawerLayout)
            )

        setupActionBarWithNavController(navController, appBarConfiguration)
        (findViewById<NavigationView>(R.id.navigationView)).setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}