package com.gokulahealth.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class VaccinationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val cattleName  = intent.getStringExtra("cattle_name") ?: "Your Cow"
        val vaccineName = intent.getStringExtra("vaccine_name") ?: "Vaccination"
        showNotification(context, cattleName, vaccineName)
    }

    private fun showNotification(
        context: Context,
        cattleName: String,
        vaccineName: String
    ) {
        val channelId = "vaccination_channel"
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create channel for Android 8+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Vaccination Reminders",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Alerts for cattle vaccination"
                enableVibration(true)
                enableLights(true)
            }
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("💉 Vaccination Due!")
            .setContentText("$cattleName needs $vaccineName today!")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("$cattleName needs $vaccineName vaccination today! Please contact your vet."))
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(
            System.currentTimeMillis().toInt(),
            notification
        )
    }
}