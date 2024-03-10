package com.example.laba1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity // makes possible to use new apps on older Android versions
import android.os.Bundle // used to store data and pass between various components of the app
import android.view.View
import android.widget.Button
import android.widget.Toast

// Main screen. An Activity is one screen
class MainActivity : AppCompatActivity() {
//    lateinit var toast: Toast // declaration to initialize this variable later
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) { // even when you rotate a new instance and onCreate is used
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // R - resources, read activity_main xml file
        //toast = Toast.makeText(this, "hello", Toast.LENGTH_SHORT)

        // Easier way to make toast
        val toast = Toast.makeText(this, "hello", Toast.LENGTH_SHORT) // toast - popup
        val btnRef: Button = findViewById(R.id.btnShowToast)
        btnRef.setOnClickListener { _ -> // read about lambda
            toast.show()
        }
        val btnRefLeft: Button = findViewById(R.id.btnLeft)
        val btnRefRight: Button = findViewById(R.id.btnRight)

//        btnRefLeft.alpha = 1f
//        btnRefRight.alpha = 0f
        btnRefLeft.visibility = View.GONE
        btnRefRight.visibility = View.VISIBLE

        btnRefLeft.setOnClickListener { _ -> // read about lambda
            btnRefLeft.visibility = View.GONE
            btnRefRight.visibility = View.VISIBLE
        }

        btnRefRight.setOnClickListener { _ -> // read about lambda
            btnRefLeft.visibility = View.VISIBLE
            btnRefRight.visibility = View.GONE
        }

    }

//    fun showToast(view: View) {
//        toast.show()
//    }
}