package com.gokulahealth

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.gokulahealth.ui.CattleListActivity
import com.gokulahealth.ui.HeatCycleActivity
import com.gokulahealth.ui.HealthPassportActivity
import com.gokulahealth.ui.MilkDiaryActivity
import com.gokulahealth.ui.VaccinationActivity
import com.gokulahealth.ui.YieldGraphActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1001
                )
            }
        }

        findViewById<Button>(R.id.btnCattleProfile).setOnClickListener {
            startActivity(Intent(this, CattleListActivity::class.java))
        }
        findViewById<Button>(R.id.btnMilkDiary).setOnClickListener {
            startActivity(Intent(this, MilkDiaryActivity::class.java))
        }
        findViewById<Button>(R.id.btnVaccination).setOnClickListener {
            startActivity(Intent(this, VaccinationActivity::class.java))
        }
        findViewById<Button>(R.id.btnYieldGraph).setOnClickListener {
            startActivity(Intent(this, YieldGraphActivity::class.java))
        }
        findViewById<Button>(R.id.btnHeatCycle).setOnClickListener {
            startActivity(Intent(this, HeatCycleActivity::class.java))
        }
        findViewById<Button>(R.id.btnHealthPassport).setOnClickListener {
            startActivity(Intent(this, HealthPassportActivity::class.java))
        }
    }
}