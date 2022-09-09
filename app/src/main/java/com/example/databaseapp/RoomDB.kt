package com.example.databaseapp

import androidx.room.*

@Entity
data class RoomShow(
    @PrimaryKey(autoGenerate = true)
    val id:Long=0,
    @ColumnInfo(name = "name")
    val showName:String,
    @ColumnInfo(name = "series")
    val showSeries:String
)

@Dao
interface RoomShowDao{
    @Query("SELECT * FROM roomshow")
    fun getAllShows():List<RoomShow>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertShows(vararg shows:RoomShow)
    @Delete
    fun deleteShow(show:RoomShow)
}

@Database(entities = [RoomShow::class], version = 1)
abstract class ShowDatabase:RoomDatabase(){
    abstract fun showDao():RoomShowDao
}