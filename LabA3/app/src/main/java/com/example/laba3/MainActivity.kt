package com.example.laba3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent
import android.provider.Settings
import android.net.Uri
import android.widget.EditText
import android.widget.ImageView
import android.graphics.Bitmap
import android.media.Image.Plane
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var settingsButton: Button
    lateinit var mapButton: Button
    lateinit var LongEditTextNumber: EditText
    lateinit var LatEditTextNumber: EditText
    lateinit var cameraButton: Button
    lateinit var imageView: ImageView

    // Dodajemy stałą do identyfikacji wyniku działania kamery
    private val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        settingsButton = findViewById(R.id.settingsButton)
        mapButton = findViewById(R.id.mapButton)
        LongEditTextNumber = findViewById(R.id.LongEditTextNumber)
        LatEditTextNumber = findViewById(R.id.LatEditTextNumber)
        cameraButton = findViewById(R.id.cameraButton)
        imageView = findViewById(R.id.imageView)

        settingsButton.setOnClickListener {
            val intent = Intent(Settings.ACTION_DISPLAY_SETTINGS)
            startActivity(intent)
        }

        mapButton.setOnClickListener {
            val latitude = LatEditTextNumber.text.toString()
            val longitude = LongEditTextNumber.text.toString()

            if(latitude == "" || longitude == ""){
                Toast.makeText(this, "Invalid map parameters", Toast.LENGTH_SHORT).show()
            } else {
                val uri = Uri.parse("geo:0,0?q=$latitude,$longitude")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                intent.setPackage("com.google.android.apps.maps")
                startActivity(intent)
            }


        }

        cameraButton.setOnClickListener {
            val intent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            val imageBitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(imageBitmap)
        }
    }
}
