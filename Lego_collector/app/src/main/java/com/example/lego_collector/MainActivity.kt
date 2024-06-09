package com.example.lego_collector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

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
                R.id.navigation_collection -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, CollectionFragment()).commit()
                    true
                }
                R.id.navigation_camera -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, CameraFragment()).commit()
                    true
                }
                R.id.navigation_wishlist -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, WishlistFragment()).commit()
                    true
                }
                else -> false
            }
        }

        // Set default selection
        bottomNavigation.selectedItemId = R.id.navigation_home
    }
}