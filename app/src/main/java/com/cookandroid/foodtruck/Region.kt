package com.cookandroid.foodtruck

import androidx.room.*

@Entity(tableName = "region")
data class Region(
    @PrimaryKey var id : Int?,
    @ColumnInfo(name = "lat") var lat : Double,
    @ColumnInfo(name = "lng") var lng : Double,
    @ColumnInfo(name = "addr") var addr : String,
    @ColumnInfo(name = "rdn") var rdn : String,
    @ColumnInfo(name = "ln") var ln : String,
    @ColumnInfo(name = "num") var num : String,
    @ColumnInfo(name = "rnt") var rnt : String,
    @ColumnInfo(name = "bgn") var bgn : String,
    @ColumnInfo(name = "end") var end : String,
    @ColumnInfo(name = "rst") var rst : String,
    @ColumnInfo(name = "ben") var ben : String,
    @ColumnInfo(name = "ins") var ins : String,
    @ColumnInfo(name = "chk") var chk : Boolean,
    @ColumnInfo(name = "vis") var vis : Boolean
) {

}