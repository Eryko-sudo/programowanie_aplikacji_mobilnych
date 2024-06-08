package com.example.lego_collector

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.lego_collector.databinding.ActivityLegoSetDetailBinding

class LegoSetDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLegoSetDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLegoSetDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve the LegoSet from the Intent
        val legoSet = intent.getSerializableExtra("legoSet") as LegoSet

        // Display the details of the LegoSet
        binding.nameTextView.text = legoSet.name
        binding.yearTextView.text = "Year: ${legoSet.year}"
        binding.themeIdTextView.text = "Theme ID: ${legoSet.theme_id}"
        binding.numPartsTextView.text = "Number of Parts: ${legoSet.num_parts}"
        Glide.with(this)
            .load(legoSet.set_img_url)
            .into(binding.imageView)
    }
}