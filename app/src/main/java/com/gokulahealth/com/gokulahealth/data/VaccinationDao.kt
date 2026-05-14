package com.gokulahealth.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface VaccinationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVaccination(vaccination: Vaccination)

    @Update
    suspend fun updateVaccination(vaccination: Vaccination)

    @Query("SELECT * FROM vaccination_table WHERE cattleId = :cattleId ORDER BY dueDate ASC")
    fun getVaccinationsByCattle(cattleId: Int): LiveData<List<Vaccination>>

    @Query("SELECT * FROM vaccination_table WHERE isCompleted = 0 ORDER BY dueDate ASC")
    fun getPendingVaccinations(): LiveData<List<Vaccination>>
}