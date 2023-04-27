package com.example.myapplication

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class bedcheck : AppCompatActivity() {

    private lateinit var row00: TextView
    private lateinit var row01: TextView
    private lateinit var row02: TextView

    private lateinit var row10: TextView
    private lateinit var row11: TextView
    private lateinit var row12: TextView
    private lateinit var firebaseAuth: FirebaseAuth
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bedcheck)
        val button1=findViewById<Button>(R.id.button4)
        val position=intent.getIntExtra("key1",0)
        val check=intent.getIntExtra("value",0)
        firebaseAuth= FirebaseAuth.getInstance()
        val database = FirebaseDatabase.getInstance()
        if(check==0){
        val myRef = database.getReference("bed")
        val id= position+1
        val userRef = myRef.child(id.toString()).child("critical")
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                        // Get the value of the key as a String
                         val value = dataSnapshot.child("total").value.toString()
                        val value1 = dataSnapshot.child("occupied").value.toString()
                         val value2 = dataSnapshot.child("vacancy").value.toString()

                row00=findViewById(R.id.txt2)
                row00.text=value
                row01=findViewById(R.id.txt3)
                row01.text=value1
                row02=findViewById(R.id.txt4)
                row02.text=value2
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Handle the error
                        Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
                    }
                })


        val userRef1 = myRef.child(id.toString()).child("normal")
        userRef1.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get the value of the key as a String
                val value3 = dataSnapshot.child("total").value.toString()
                val value4 = dataSnapshot.child("occupied").value.toString()
                val value5 = dataSnapshot.child("vacancy").value.toString()

                row10=findViewById(R.id.txt6)
                row10.text=value3
                row11=findViewById(R.id.txt7)
                row11.text=value4
                row12=findViewById(R.id.txt8)
                row12.text=value5
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }
        })}
        else{
            val myRef = database.getReference("bed1")
            val id= position+1
            val userRef = myRef.child(id.toString()).child("critical")
            userRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // Get the value of the key as a String
                    val value = dataSnapshot.child("total").value.toString()
                    val value1 = dataSnapshot.child("occupied").value.toString()
                    val value2 = dataSnapshot.child("vacancy").value.toString()

                    row00=findViewById(R.id.txt2)
                    row00.text=value
                    row01=findViewById(R.id.txt3)
                    row01.text=value1
                    row02=findViewById(R.id.txt4)
                    row02.text=value2
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle the error
                    Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
                }
            })


            val userRef1 = myRef.child(id.toString()).child("normal")
            userRef1.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // Get the value of the key as a String
                    val value3 = dataSnapshot.child("total").value.toString()
                    val value4 = dataSnapshot.child("occupied").value.toString()
                    val value5 = dataSnapshot.child("vacancy").value.toString()

                    row10=findViewById(R.id.txt6)
                    row10.text=value3
                    row11=findViewById(R.id.txt7)
                    row11.text=value4
                    row12=findViewById(R.id.txt8)
                    row12.text=value5
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle the error
                    Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
                }
            })
        }
        button1.setOnClickListener{
            val intent= Intent(this,quiz::class.java)
            intent.putExtra("key1",position)
            intent.putExtra("value",check)
            startActivity(intent)
        }


    }
}