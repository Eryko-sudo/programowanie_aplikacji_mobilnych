package com.example.laba2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    lateinit var editNum1:EditText;
    lateinit var editNum2:EditText;
    lateinit var textResult: TextView;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun addNumbers(view: View?) {
        editNum1 = findViewById(R.id.editNum1);
        editNum2 = findViewById(R.id.editNum2);
        textResult = findViewById(R.id.textResult);
        val num1 = editNum1.text.toString().toDouble();
        val num2 = editNum2.text.toString().toDouble();
        val result = num1 + num2;
        textResult.text = java.lang.Double.toString(result);

    }
}