package com.example.lego_collector

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class EnlargeImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enlarge_image)

        val bitmap = intent.getParcelableExtra<Bitmap>("image")
        findViewById<ImageView>(R.id.enlargedImage).setImageBitmap(bitmap)
    }
}