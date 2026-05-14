package com.gokulahealth.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gokulahealth.R
import com.gokulahealth.adapter.CattleAdapter
import com.gokulahealth.data.AppDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CattleListActivity : AppCompatActivity() {
    private lateinit var adapter: CattleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cattle_list)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerCattle)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = CattleAdapter { cattle ->
            val intent = Intent(this, MilkDiaryActivity::class.java)
            intent.putExtra("cattle_id", cattle.id)
            intent.putExtra("cattle_name", cattle.name)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        val db = AppDatabase.getDatabase(this)
        db.cattleDao().getAllCattle().observe(this) { cattleList ->
            adapter.submitList(cattleList)
        }

        findViewById<FloatingActionButton>(R.id.fabAddCattle).setOnClickListener {
            startActivity(Intent(this, AddCattleActivity::class.java))
        }
    }
}