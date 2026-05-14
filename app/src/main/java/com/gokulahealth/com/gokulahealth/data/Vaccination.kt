package com.gokulahealth.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vaccination_table")
data class Vaccination(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val cattleId: Int,
    val vaccineName: String,
    val dueDate: String,
    val isCompleted: Boolean = false,
    val notes: String = ""
)