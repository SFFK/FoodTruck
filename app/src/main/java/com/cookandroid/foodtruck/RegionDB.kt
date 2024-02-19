package com.cookandroid.foodtruck

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Region::class], version = 3)
abstract class RegionDB : RoomDatabase() {
    abstract fun regionDao() : RegionDao

    companion object {
        private var INSTANCE : RegionDB? = null

        fun getInstance(context : Context) : RegionDB? {
            if(INSTANCE == null) {
                synchronized(RegionDB::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, RegionDB::class.java, "region.db").addMigrations().fallbackToDestructiveMigration().build()
                }
            }
            return INSTANCE
        }

        fun destroyInstacne() {
            INSTANCE = null
        }
    }
}