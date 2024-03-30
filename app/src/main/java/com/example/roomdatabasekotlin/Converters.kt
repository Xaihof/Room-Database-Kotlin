package com.example.roomdatabasekotlin

import androidx.room.TypeConverter
import java.util.Date

class Converters {

    /*We cannot save Date datatype directly into RoomDB.
     We convert it into Long and then when we retrieve it it is again converted into Date by converters Annotation in ContactDatabase.*/

    @TypeConverter
    fun fromDateToLong(value: Date): Long {
        return value.time
    }

    @TypeConverter
    fun fromLongToDate(value: Long): Date {
        return Date(value)
    }
}