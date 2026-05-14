package com.gokulahealth.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface HeatCycleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHeatCycle(heatCycle: HeatCycle)

    @Update
    suspend fun updateHeatCycle(heatCycle: HeatCycle)

    @Query("SELECT * FROM heat_cycle_table WHERE cattleId = :cattleId ORDER BY lastHeatDate DESC")
    fun getHeatCyclesByCattle(cattleId: Int): LiveData<List<HeatCycle>>

    @Query("SELECT * FROM heat_cycle_table ORDER BY nextHeatDate ASC")
    fun getAllUpcomingHeatCycles(): LiveData<List<HeatCycle>>
}