package com.gokulahealth.ui

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.gokulahealth.R
import com.gokulahealth.data.AppDatabase
import com.gokulahealth.data.Vaccination
import com.gokulahealth.receiver.VaccinationReceiver
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class VaccinationActivity : AppCompatActivity() {
    private var cattleId: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vaccination)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1001
                )
            }
        }

        cattleId = intent.getIntExtra("cattle_id", 1)

        val etVaccineName = findViewById<TextInputEditText>(R.id.etVaccineName)
        val etDueDate     = findViewById<TextInputEditText>(R.id.etDueDate)
        val db            = AppDatabase.getDatabase(this)

        val tvList = TextView(this).apply {
            textSize = 15f
            setPadding(16, 16, 16, 16)
            setTextColor(android.graphics.Color.parseColor("#333333"))
        }

        db.vaccinationDao().getPendingVaccinations().observe(this) { list ->
            if (list.isNullOrEmpty()) {
                tvList.text = "No pending vaccinations"
            } else {
                tvList.text = list.joinToString("\n\n") {
                    "💉 ${it.vaccineName}\n📅 Due: ${it.dueDate}"
                }
            }
        }

        val rootLayout = findViewById<LinearLayout>(R.id.vaccinationRoot)
        rootLayout.addView(tvList)

        findViewById<Button>(R.id.btnSetAlarm).setOnClickListener {
            val vacName = etVaccineName.text.toString().trim()
            val dueDate = etDueDate.text.toString().trim()

            if (vacName.isEmpty() || dueDate.isEmpty()) {
                Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                db.vaccinationDao().insertVaccination(
                    Vaccination(
                        cattleId    = cattleId,
                        vaccineName = vacName,
                        dueDate     = dueDate
                    )
                )
            }

            scheduleAlarm(vacName, dueDate)
            Toast.makeText(this,
                "✅ Reminder set for $vacName on $dueDate",
                Toast.LENGTH_LONG).show()
            etVaccineName.text?.clear()
            etDueDate.text?.clear()
        }
    }

    private fun scheduleAlarm(vacName: String, dueDateStr: String) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(this, VaccinationReceiver::class.java).apply {
            putExtra("vaccine_name", vacName)
            putExtra("cattle_name", "Your Cow")
        }

        val pendingIntent = PendingIntent.getBroadcast(
            this,
            System.currentTimeMillis().toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Always fire in 5 seconds for testing
        val triggerTime = System.currentTimeMillis() + 5000L

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent
                    )
                } else {
                    alarmManager.set(
                        AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent
                    )
                }
            } else {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent
                )
            }
        } catch (e: Exception) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
        }
    }
}