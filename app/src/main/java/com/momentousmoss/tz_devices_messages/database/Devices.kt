package com.momentousmoss.tz_devices_messages.database

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update

@Dao
interface DevicesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDevice(device: DevicesEntity)

    @Query("SELECT * FROM DevicesEntity")
    fun getDevices(): Cursor

    @Query("SELECT * FROM DevicesEntity WHERE status = :status")
    fun getDevicesByStatus(status: String): Cursor

    @Query("DELETE FROM DevicesEntity")
    fun clear()
}

@Entity
class DevicesEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var name: String,
    var type: String,
    var status: String,
    var mac: String,
    var subscriptions: String
)