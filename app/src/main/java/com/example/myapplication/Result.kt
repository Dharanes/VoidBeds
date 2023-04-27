package com.example.myapplication

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
class Result : AppCompatActivity() {

    private lateinit var row: TextView
    lateinit var message:String
    lateinit var phoneNumber: String

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        row = findViewById(R.id.name1)
        val Button1 = findViewById<Button>(R.id.button5)
        Button1.setOnClickListener {
            val intent = Intent(this, navigation_drawer::class.java)
            startActivity(intent)
        }
        val name1 = intent.getIntExtra("k",0)

        val number1 = intent.getStringExtra("k1")
        message="Bed has been alloted successfully"
        val phoneNumber = number1.toString()

            if (name1 == 0) {
                row.text = "Sorry !! bed is not available"
            } else {
                row.text = "Bed has been alloted successfully"
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.SEND_SMS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // Request permission
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.SEND_SMS),
                        PERMISSION_REQUEST_CODE
                    )
                } else {
                    // Permission already granted
                    sendMessage(phoneNumber, message)
                }
            }
        }

        private fun sendMessage(phoneNumber: String, message: String) {
            try {
                val smsManager: SmsManager = SmsManager.getDefault()
                smsManager.sendTextMessage(phoneNumber, null, message, null, null)
                Toast.makeText(this, "Message sent successfully.", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this, "Error occurred while sending message.", Toast.LENGTH_SHORT)
                    .show()
                e.printStackTrace()
            }
        }

        override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
        ) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)

            if (requestCode == PERMISSION_REQUEST_CODE) {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    val phoneNumber1 =phoneNumber
                    val message1 = message
                    sendMessage(phoneNumber1, message1)
                } else {
                    Toast.makeText(this, "Permission denied.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }