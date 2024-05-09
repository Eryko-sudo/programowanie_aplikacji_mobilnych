package com.example.laba2

import android.media.Rating
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.location.LocationRequestCompat.Quality

class MainActivity : AppCompatActivity() {
    lateinit var editPrice: EditText
    lateinit var editTip: EditText
    lateinit var textTip: TextView
    lateinit var textResult: TextView
    lateinit var foodRatingBar: RatingBar
    lateinit var serviceRatingBar: RatingBar
    lateinit var calculateButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        foodRatingBar = findViewById<RatingBar>(R.id.foodRatingBar)
        serviceRatingBar = findViewById<RatingBar>(R.id.serviceRatingBar)
        editPrice = findViewById(R.id.editPrice)
        editTip = findViewById(R.id.editTip)
        textResult = findViewById(R.id.textResult)
        textTip = findViewById(R.id.textTip)
        calculateButton = findViewById(R.id.calculateButton)

        calculateButton.setOnClickListener {
            if(editPrice.text.toString() != "" && editTip.text.toString() != "" && serviceRatingBar.rating.toString() != ""){
                val foodPrice = editPrice.text.toString().toDouble()
                val tipPercentage = editTip.text.toString().toDouble()
                val serviceQuality = serviceRatingBar.rating.toDouble()
                if(foodPrice != null && tipPercentage != null && serviceQuality != null){
                    val tipAmount = calculateTip(foodPrice, tipPercentage, serviceQuality)
                    calculateFinalPrice(foodPrice, tipAmount)
                }
            }
            else {
                Toast.makeText(this@MainActivity, "Wrong data input! Check the values.", Toast.LENGTH_SHORT).show()
            }



        }
    }

    fun calculateTip(foodPrice: Double, tipPercentage: Double, serviceQuality: Double): Double{
        val tipValue = foodPrice * (tipPercentage + (serviceQuality - 3) * 2) / 100 // One star -4%, Two stars -2%, Three stars 0%, Four stars +2%, Five stars +4%
        return tipValue
    }
    fun calculateFinalPrice(foodPrice: Double, tipValue: Double) {
        val finalPrice = foodPrice + tipValue
        textResult.text = java.lang.Double.toString(finalPrice)
        textTip.text = java.lang.Double.toString(tipValue)

    }
}