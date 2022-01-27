package com.codingnight.example.navigationdrawer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.fragment)

//        appBarConfiguration =
//            01_AppBarConfiguration(navController.graph, findViewById(R.id.drawerLayout))

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