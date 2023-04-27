package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityNavigationDrawerBinding
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset

class navigation_drawer : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityNavigationDrawerBinding
    private lateinit var navigationView: NavigationView
    private lateinit var newRecylerview : RecyclerView
    private lateinit var adapter: Myadapter
    var newArrayList = ArrayList<hospital>()
    private lateinit var news : Array<String>
    private lateinit var imageId : Array<Int>
    lateinit var heading : Array<String>
    lateinit var name: Array<String>

    private lateinit var hName: String
    private lateinit var hDesc: String
    private lateinit var hTiming: String

    private lateinit var listview: ListView
    var selectedItem = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNavigationDrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarNavigationDrawer.toolbar)

        binding.appBarNavigationDrawer.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_navigation_drawer)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navigationView = findViewById(R.id.nav_view)

        navigationView.setNavigationItemSelectedListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.nav_gallery-> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                }
            }
            false
        }
        imageId = arrayOf(
            R.drawable.apolo,
            R.drawable.srm,
            R.drawable.government,
            R.drawable.cmc
        )

        heading = arrayOf(
            "Apollo hospital trichy Ariyamangalam Area, Chennai - Madurai Highway, Trichy ",

            "Trichy SRM Medical College Hospital Research Center, SRM Nagar, Trichy – Chennai Highway",
            "Government medical college",
            "Christian Medical College Vellore, INDIA, 632002")

        newRecylerview =findViewById(R.id.recyclerView)


        newRecylerview.layoutManager = LinearLayoutManager(this)
        newRecylerview.setHasFixedSize(true)
        val positionToScroll = 5
        newRecylerview.smoothScrollToPosition(positionToScroll)
        // Attach the adapter to the RecyclerView
        //newRecylerview.adapter = adapter

        newArrayList = arrayListOf<hospital>()

        getUserdata()


        val search=findViewById<SearchView>(R.id.search)
        listview =findViewById<ListView>(R.id.listview)


//        var name=arrayOf("trichy","coimbatore","vellore")
        val adapter1= ArrayAdapter(this, android.R.layout.simple_list_item_1, name)
        news = arrayOf(
            getString(R.string.appolo),
            getString(R.string.cmc),
            getString(R.string.srm),
            getString(R.string.gcc)
        )

        listview.adapter=adapter1
        listview.visibility = View.GONE

        search.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                search.clearFocus()

                if(query != null && query.isNotBlank()){
                    if(name.contains(query.toLowerCase())){
                        listview.visibility = View.VISIBLE
                        adapter1.filter.filter(query)
                    }
                    else{
                        Toast.makeText(applicationContext,"item not found", Toast.LENGTH_LONG).show()
                    }}
                else{
                    adapter1 .clear()
                    listview.visibility = View.GONE
                }

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter1.filter.filter(newText)
                return false
            }
        })

        listview.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            // Get the selected item from the adapter
            selectedItem = (parent.adapter).getItem(position).toString()
            Log.i("SelectedItem",selectedItem)
            // Start the new activity with the selected item
            getUserdata()
            if(selectedItem=="trichy"){
                val intent= Intent(this,page2::class.java)
                intent.putExtra("hospitalName",selectedItem)
                startActivity(intent)
            }
            else if(selectedItem=="coimbatore"){
                val intent= Intent(this,page1::class.java)
                startActivity(intent)
            }
            else if(selectedItem=="vellore"){
                val intent= Intent(this,page1::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.navigation_drawer, menu)
        return true
    }
    private fun getUserdata() {

        try{
            val obj = JSONObject(getJSONFromAssets()!!)
            val userArray = obj.getJSONArray("hospitals")
            name = Array<String>(userArray.length()){""}
            for( i in 0 until userArray.length()){

                val user = userArray.getJSONObject(i)
                hName = user.getString("name")
                hDesc = user.getString("description")
                hTiming = user.getString("state")
                val hType = user.getString("category")
//                val hImage = user.getString("imagename")
                name[i] = hTiming
                if(selectedItem!=""){
                    val userDetails = hospital(R.drawable.h1,hName,hTiming)
                    newArrayList.add(userDetails)
                    continue
                }
                val userDetails = hospital(R.drawable.h1,hName,hTiming)
                newArrayList.add(userDetails)

            }

        }catch (e : JSONException){
            e.printStackTrace()
        }
//        for(i in imageId.indices){
//
//            val news = hospital(imageId[i],heading[i])
//            newArrayList.add(news)
//
//        }



        newRecylerview.adapter=Myadapter(newArrayList)
        val adapter=Myadapter(newArrayList)
        newRecylerview.adapter=adapter
        //button click
        adapter.setOnItemClickListener(object:Myadapter.onItemClickListener{
            override fun onItemClick(position: Int) {

                val intent = Intent(this@navigation_drawer,NewsActivity::class.java)
                intent.putExtra("heading",newArrayList[position].heading)
                intent.putExtra("imageId",newArrayList[position].titleImage)
                intent.putExtra("news",news[position])
                startActivity(intent)

            }
        })
    }



    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_navigation_drawer)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId
        if (id == R.id.nav_gallery) {

            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
            return true
        }
        else{
            return super.onOptionsItemSelected(item)}

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