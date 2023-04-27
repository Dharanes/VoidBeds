package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset
import java.util.*
import kotlin.collections.ArrayList
class page2 : AppCompatActivity() {
    private lateinit var newRecylerview : RecyclerView
    private lateinit var adapter: Myadapter
    private lateinit var newArrayLists : ArrayList<hospital>
    private lateinit var lists : ArrayList<hospital>
    private lateinit var news : Array<String>
    lateinit var name: Array<String>
    lateinit var imageId : Array<Int>
    lateinit var heading : Array<String>
    private lateinit var hName: String
    private lateinit var hDesc: String
    var hosName = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page2)

        hosName = intent.getStringExtra("hospitalName").toString()

        var rv = navigation_drawer()
        lists = rv.newArrayList
        imageId = arrayOf(
            R.drawable.apolo,
            R.drawable.srm,
            R.drawable.government
        )

        heading = arrayOf(
            "APOLLO HOSPITAL TRICHY",
            "SRM TRICHY",
            "GOVERNMENT MEDICAL COLLEGE TRICHY")
        news = arrayOf(
            getString(R.string.appolo),
            getString(R.string.srm),
            getString(R.string.gcc)
        )
        newRecylerview =findViewById(R.id.recyclerView)


        newRecylerview.layoutManager = LinearLayoutManager(this)
        newRecylerview.setHasFixedSize(true)
        val positionToScroll = 5
        newRecylerview.smoothScrollToPosition(positionToScroll)
        // Attach the adapter to the RecyclerView
        //newRecylerview.adapter = adapter

        newArrayLists = arrayListOf<hospital>()

        getUserdata()

    }
    @SuppressLint("SuspiciousIndentation")
    private fun getUserdata() {

//        for(i in lists){
//            if(i.state.equals(hosName)) {
//                val news = hospital(R.drawable.h1, hName,hosName)
//                newArrayLists.add(news)
//            }
//        }

        try{
            val obj = JSONObject(getJSONFromAssets()!!)
            val userArray = obj.getJSONArray("hospitals")
            name = Array<String>(userArray.length()){""}
            for( i in 0 until userArray.length()){

                val user = userArray.getJSONObject(i)
                hName = user.getString("name")
                hDesc = user.getString("description")
                val hTiming = user.getString("state")
                val hType = user.getString("category")
//                val hImage = user.getString("imagename")
                name[i] = hTiming

                if(hTiming.equals(hosName)) {
                    val userDetails = hospital(R.drawable.h1, hName, "")
                    newArrayLists.add(userDetails)
                }
            }

        }catch (e : JSONException){
            e.printStackTrace()
        }

        var adapter=Myadapter(newArrayLists)
        newRecylerview.adapter=adapter
        //button click
        adapter.setOnItemClickListener(object:Myadapter.onItemClickListener{
            override fun onItemClick(position: Int) {


                val intent = Intent(this@page2,NewsActivity::class.java)
                intent.putExtra("heading",newArrayLists[position].heading)
                intent.putExtra("imageId",newArrayLists[position].titleImage)
                intent.putExtra("news",news[position])
                intent.putExtra("key1",position)



                startActivity(intent)

            }})

    }

    private fun getJSONFromAssets(): String? {
        var json: String? = null
        val charset: Charset = Charsets.UTF_8
        try{
            val `is` = assets.open("landmarkData.json")
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            json = String(buffer,charset)
        }catch (e: IOException){
            e.printStackTrace()
        }
        return json
    }
}