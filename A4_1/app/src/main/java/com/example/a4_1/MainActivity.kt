package com.example.a4_1

import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.content.BroadcastReceiver
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private val batteryReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                Intent.ACTION_BATTERY_LOW -> {
                    Toast.makeText(context, "Battery is low", Toast.LENGTH_SHORT).show()
                }
                Intent.ACTION_BATTERY_OKAY -> {
                    Toast.makeText(context, "Battery is okay", Toast.LENGTH_SHORT).show()
                }
                Intent.ACTION_POWER_CONNECTED -> {
                    Toast.makeText(context, "Power connected", Toast.LENGTH_SHORT).show()
                }
                Intent.ACTION_POWER_DISCONNECTED -> {
                    Toast.makeText(context, "Power disconnected", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Register the receiver
        val intentFilter = IntentFilter().apply {
            addAction(Intent.ACTION_BATTERY_LOW)
            addAction(Intent.ACTION_BATTERY_OKAY)
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
        }
        registerReceiver(batteryReceiver, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Unregister the receiver
        unregisterReceiver(batteryReceiver)
    }
}