package com.gokulahealth.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "heat_cycle_table")
data class HeatCycle(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val cattleId: Int,
    val lastHeatDate: String,
    val nextHeatDate: String,
    val breedingDate: String = "",
    val isBreedingDone: Boolean = false,
    val notes: String = ""
)