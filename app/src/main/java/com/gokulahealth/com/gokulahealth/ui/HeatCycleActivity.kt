package com.gokulahealth.ui

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.gokulahealth.R
import com.gokulahealth.data.AppDatabase
import com.gokulahealth.data.HeatCycle
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class HeatCycleActivity : AppCompatActivity() {
    private var cattleId: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heat_cycle)

        cattleId = intent.getIntExtra("cattle_id", 1)
        val cattleName = intent.getStringExtra("cattle_name") ?: "Cow"
        title = "🌡️ $cattleName - Heat Cycle"

        val etLastHeatDate  = findViewById<TextInputEditText>(R.id.etLastHeatDate)
        val etBreedingDate  = findViewById<TextInputEditText>(R.id.etBreedingDate)
        val etNotes         = findViewById<TextInputEditText>(R.id.etHeatNotes)
        val tvNextHeatDate  = findViewById<TextView>(R.id.tvNextHeatDate)
        val tvHeatCycleList = findViewById<TextView>(R.id.tvHeatCycleList)
        val db              = AppDatabase.getDatabase(this)

        db.heatCycleDao().getAllUpcomingHeatCycles().observe(this) { list ->
            if (list.isNullOrEmpty()) {
                tvHeatCycleList.text = "No heat cycle records yet"
            } else {
                tvHeatCycleList.text = list.joinToString("\n\n") {
                    "🌡️ Last Heat: ${it.lastHeatDate}\n" +
                            "📅 Next Heat: ${it.nextHeatDate}\n" +
                            if (it.isBreedingDone) "✅ Breeding Done: ${it.breedingDate}"
                            else "⏳ Breeding Pending"
                }
            }
        }

        etLastHeatDate.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val lastDate = etLastHeatDate.text.toString().trim()
                if (lastDate.isNotEmpty()) {
                    val nextDate = calculateNextHeatDate(lastDate)
                    tvNextHeatDate.text = "📅 Next Heat Date: $nextDate"
                }
            }
        }

        findViewById<Button>(R.id.btnSaveHeatCycle).setOnClickListener {
            val lastHeatDate = etLastHeatDate.text.toString().trim()
            val breedingDate = etBreedingDate.text.toString().trim()
            val notes        = etNotes.text.toString().trim()

            if (lastHeatDate.isEmpty()) {
                Toast.makeText(this,
                    "Please enter last heat date!",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nextHeatDate = calculateNextHeatDate(lastHeatDate)

            val heatCycle = HeatCycle(
                cattleId       = cattleId,
                lastHeatDate   = lastHeatDate,
                nextHeatDate   = nextHeatDate,
                breedingDate   = breedingDate,
                isBreedingDone = breedingDate.isNotEmpty(),
                notes          = notes
            )

            lifecycleScope.launch {
                db.heatCycleDao().insertHeatCycle(heatCycle)
                runOnUiThread {
                    Toast.makeText(this@HeatCycleActivity,
                        "✅ Saved! Next heat: $nextHeatDate",
                        Toast.LENGTH_LONG).show()
                    etLastHeatDate.text?.clear()
                    etBreedingDate.text?.clear()
                    etNotes.text?.clear()
                    tvNextHeatDate.text = "📅 Next Heat Date: $nextHeatDate"
                }
            }
        }
    }

    private fun calculateNextHeatDate(lastHeatDateStr: String): String {
        return try {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val lastDate = sdf.parse(lastHeatDateStr) ?: return ""
            val calendar = Calendar.getInstance()
            calendar.time = lastDate
            calendar.add(Calendar.DAY_OF_MONTH, 21)
            sdf.format(calendar.time)
        } catch (e: Exception) {
            ""
        }
    }
}