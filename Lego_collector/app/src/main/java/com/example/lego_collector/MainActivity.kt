package com.example.lego_collector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.lego_collector.HomeFragment
import com.example.lego_collector.DashboardFragment
import com.example.lego_collector.CameraFragment
import com.example.lego_collector.ProfileFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, HomeFragment()).commit()
                    true
                }
                R.id.navigation_dashboard -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, DashboardFragment()).commit()
                    true
                }
                R.id.navigation_camera -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, CameraFragment()).commit()
                    true
                }
                R.id.navigation_profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, ProfileFragment()).commit()
                    true
                }
                else -> false
            }
        }

        // Set default selection
        bottomNavigation.selectedItemId = R.id.navigation_home
    }
}