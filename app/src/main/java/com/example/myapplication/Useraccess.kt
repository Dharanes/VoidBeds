package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Useraccess : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_useraccess)
        val button=findViewById<Button>(R.id.button6)
        val button1=findViewById<Button>(R.id.button7)
        button.setOnClickListener{
            val intent= Intent(this,MapsActivity::class.java)
            startActivity(intent)
        }
        button1.setOnClickListener{
            val intent= Intent(this,navigation_drawer::class.java)
            startActivity(intent)
        }
    }
}