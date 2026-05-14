package com.gokulahealth.ui

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gokulahealth.R
import com.gokulahealth.adapter.MilkAdapter
import com.gokulahealth.data.AppDatabase
import com.gokulahealth.data.MilkEntry
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MilkDiaryActivity : AppCompatActivity() {
    private var cattleId: Int = 1
    private lateinit var adapter: MilkAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_milk_diary)

        cattleId = intent.getIntExtra("cattle_id", 1)
        val cattleName = intent.getStringExtra("cattle_name") ?: "Cow"
        title = "🥛 $cattleName"

        val etMorning = findViewById<TextInputEditText>(R.id.etMorning)
        val etEvening = findViewById<TextInputEditText>(R.id.etEvening)
        val tvAvg     = findViewById<TextView>(R.id.tvMonthlyAvg)

        val recycler = findViewById<RecyclerView>(R.id.recyclerMilk)
        recycler.layoutManager = LinearLayoutManager(this)
        adapter = MilkAdapter()
        recycler.adapter = adapter

        val db = AppDatabase.getDatabase(this)

        db.milkDao().getMilkByCattle(cattleId).observe(this) { entries ->
            adapter.submitList(entries)
        }

        val monthPattern = "%${SimpleDateFormat("MM/yyyy", Locale.getDefault()).format(Date())}%"
        db.milkDao().getMonthlyAverage(cattleId, monthPattern).observe(this) { avg ->
            tvAvg.text = if (avg != null) String.format("%.2f liters/day", avg)
            else "-- liters/day"
        }

        findViewById<Button>(R.id.btnSaveMilk).setOnClickListener {
            val morning = etMorning.text.toString().toFloatOrNull() ?: 0f
            val evening = etEvening.text.toString().toFloatOrNull() ?: 0f
            val today   = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

            val entry = MilkEntry(
                cattleId = cattleId,
                date = today,
                morningLiters = morning,
                eveningLiters = evening
            )
            lifecycleScope.launch {
                db.milkDao().insertMilkEntry(entry)
                runOnUiThread {
                    etMorning.text?.clear()
                    etEvening.text?.clear()
                    Toast.makeText(this@MilkDiaryActivity,
                        "✅ Saved! Total: ${morning + evening}L", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}