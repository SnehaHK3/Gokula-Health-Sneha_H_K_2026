package com.gokulahealth.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "milk_table")
data class MilkEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val cattleId: Int,
    val date: String,
    val morningLiters: Float,
    val eveningLiters: Float,
    val totalLiters: Float = morningLiters + eveningLiters
)