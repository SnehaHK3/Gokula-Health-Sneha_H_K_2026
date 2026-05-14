package com.gokulahealth.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MilkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMilkEntry(entry: MilkEntry)

    @Query("SELECT * FROM milk_table WHERE cattleId = :cattleId ORDER BY date DESC")
    fun getMilkByCattle(cattleId: Int): LiveData<List<MilkEntry>>

    @Query("SELECT * FROM milk_table WHERE cattleId = :cattleId ORDER BY date DESC LIMIT 30")
    fun getLast30Days(cattleId: Int): LiveData<List<MilkEntry>>

    @Query("SELECT AVG(totalLiters) FROM milk_table WHERE cattleId = :cattleId AND date LIKE :monthPattern")
    fun getMonthlyAverage(cattleId: Int, monthPattern: String): LiveData<Float>
}