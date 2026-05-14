package com.gokulahealth.ui

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.components.Description
import com.gokulahealth.R
import com.gokulahealth.data.AppDatabase

class YieldGraphActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yield_graph)

        val cattleId  = intent.getIntExtra("cattle_id", 1)
        val lineChart = findViewById<LineChart>(R.id.lineChart)
        val tvAvg     = findViewById<TextView>(R.id.tvAvgOnGraph)

        AppDatabase.getDatabase(this).milkDao().getLast30Days(cattleId).observe(this) { entries ->
            if (entries.isNullOrEmpty()) return@observe

            val chartEntries = entries.reversed().mapIndexed { index, milkEntry ->
                Entry(index.toFloat(), milkEntry.totalLiters)
            }

            val dataSet = LineDataSet(chartEntries, "Daily Milk (Liters)").apply {
                color = Color.parseColor("#2196F3")
                lineWidth = 2.5f
                circleRadius = 4f
                setCircleColor(Color.parseColor("#2196F3"))
                setDrawFilled(true)
                fillColor = Color.parseColor("#BBDEFB")
            }

            lineChart.apply {
                data = LineData(dataSet)
                val desc = Description()
                desc.text = "Last 30 Days"
                description = desc
                animateX(1000)
                invalidate()
            }

            val avg = entries.map { it.totalLiters }.average()
            tvAvg.text = String.format("Monthly Average: %.2f liters/day", avg)
        }
    }
}