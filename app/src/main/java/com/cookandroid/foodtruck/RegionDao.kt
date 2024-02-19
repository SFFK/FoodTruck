package com.cookandroid.foodtruck

import androidx.room.*

@Dao
interface RegionDao {
    @Query("SELECT * FROM region")
    suspend fun getAll() : MutableList<Region>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(region : Region)

    @Update
    suspend fun update(region : Region)

    @Query("UPDATE region SET chk = 0")
    suspend fun updateChk()

    @Query("UPDATE region SET vis = 1")
    suspend fun updateVis()

    @Query("UPDATE region SET vis = 0")
    suspend fun updateInv()

    @Query("DELETE FROM region")
    suspend fun deleteAll()
}