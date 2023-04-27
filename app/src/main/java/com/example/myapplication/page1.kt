package com.example.myapplication



import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList
class page1 : AppCompatActivity() {
    private lateinit var newRecylerview : RecyclerView
    private lateinit var adapter: Myadapter
    private lateinit var newArrayList : ArrayList<hospital>
    private lateinit var news : Array<String>
    lateinit var imageId : Array<Int>
    lateinit var heading : Array<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page1)
        imageId = arrayOf(
            R.drawable.apolo,
            R.drawable.srm,
            R.drawable.government ,
            R.drawable.cmc
        )

        heading = arrayOf(
            "APOLLO HOSPITAL TRICHY",
            "SRM TRICHY",
            "GOVERNMENT MEDICAL COLLEGE TRICHY",
            "CMC VELLORE")
        news = arrayOf(
            getString(R.string.appolo),
            getString(R.string.srm),
            getString(R.string.gcc),
            getString(R.string.cmc),
        )
        newRecylerview =findViewById(R.id.recyclerView)


        newRecylerview.layoutManager = LinearLayoutManager(this)
        newRecylerview.setHasFixedSize(true)
        val positionToScroll = 5
        newRecylerview.smoothScrollToPosition(positionToScroll)
        // Attach the adapter to the RecyclerView
        //newRecylerview.adapter = adapter

        newArrayList = arrayListOf<hospital>()

        getUserdata()

    }
    @SuppressLint("SuspiciousIndentation")
    private fun getUserdata() {

        for(i in imageId.indices){

            val news = hospital(imageId[i],heading[i],"")
            newArrayList.add(news)

        }
        var adapter=Myadapter(newArrayList)
        newRecylerview.adapter=adapter
        //button click
        adapter.setOnItemClickListener(object:Myadapter.onItemClickListener{
            override fun onItemClick(position: Int) {

                val intent = Intent(this@page1,NewsActivity::class.java)
                intent.putExtra("heading",newArrayList[position].heading)
                intent.putExtra("imageId",newArrayList[position].titleImage)
                intent.putExtra("news",news[position])

                startActivity(intent)

            }
        })
    }
}