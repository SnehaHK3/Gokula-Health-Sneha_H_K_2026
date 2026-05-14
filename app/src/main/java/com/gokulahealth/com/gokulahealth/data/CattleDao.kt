package com.gokulahealth.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CattleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCattle(cattle: Cattle)

    @Update
    suspend fun updateCattle(cattle: Cattle)

    @Delete
    suspend fun deleteCattle(cattle: Cattle)

    @Query("SELECT * FROM cattle_table ORDER BY name ASC")
    fun getAllCattle(): LiveData<List<Cattle>>

    @Query("SELECT * FROM cattle_table WHERE id = :cattleId")
    fun getCattleById(cattleId: Int): LiveData<Cattle>
}