package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class page4 : AppCompatActivity() {
    private lateinit var newRecylerview : RecyclerView
    private lateinit var adapter: Myadapter
    private lateinit var newArrayList : ArrayList<hospital1>
    private lateinit var news : Array<String>

    lateinit var imageId : Array<Int>
    lateinit var heading : Array<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page4)
        imageId = arrayOf(
            R.drawable.meenakshi,

            R.drawable.kj,
            R.drawable.ramakrishna
        )

        heading = arrayOf(
            "Sree Meenakshi Hospitals",
            "K .J. Hospital",
            "Sri Ramakrishna Hospital")
        news = arrayOf(
            "Sree Meenakshi Hospitals 269, Palakkad Main Rd, Kuniamuthur, Coimbatore",
            "K .J. Hospital W-59, Near Ayyappan Kovil, Opposite Police Camp, W- Block, Kovai Pudur, Coimbatore",
            "Sri Ramakrishna Hospital Gandhipuram, Coimbatore 395, Sarojini Naidu Road, Siddhapudur"
        )
        newRecylerview =findViewById(R.id.recyclerView)


        newRecylerview.layoutManager = LinearLayoutManager(this)
        newRecylerview.setHasFixedSize(true)
        val positionToScroll = 5
        newRecylerview.smoothScrollToPosition(positionToScroll)
        // Attach the adapter to the RecyclerView
        //newRecylerview.adapter = adapter

        newArrayList = arrayListOf<hospital1>()

        getUserdata()

    }
    @SuppressLint("SuspiciousIndentation")
    private fun getUserdata() {

        for(i in imageId.indices){

            val news = hospital1(imageId[i],heading[i])
            newArrayList.add(news)

        }
        var adapter=Myadapter1(newArrayList)
        newRecylerview.adapter=adapter
        //button click
        adapter.setOnItemClickListener(object:Myadapter1.onItemClickListener{
            override fun onItemClick(position: Int) {


                val intent = Intent(this@page4,NewsActivity1::class.java)
                intent.putExtra("heading",newArrayList[position].heading)
                intent.putExtra("imageId",newArrayList[position].titleImage)
                intent.putExtra("news",news[position])
                intent.putExtra("key1",position)



                startActivity(intent)

            }})

    }}