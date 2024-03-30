package com.example.roomdatabasekotlin

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Contact::class], version = 2)
@TypeConverters(Converters::class)
abstract class ContactDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDAO

    companion object {

        // This code(migration form version 1 to version 2) will execute only when your app was already installed on user's device. By default it will create version 2.
        val migration_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE contact ADD COLUMN isActive INTEGER NOT NULL DEFAULT(1)")
            }

        }


        // As soon as the data is written in this variable This annotation will provide the updated value of this variable to all the threads.
        @Volatile
        private var INSTANCE: ContactDatabase? = null

        // with this function other classes can access the private database object.(i.e singleton pattern.)
        fun getDatabase(context: Context): ContactDatabase {

            if (INSTANCE == null) {
                // This will prevent mltiple threads from making instance at the sametime.
                synchronized(this) {
                    // making instance of the database.
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext, ContactDatabase::class.java, "contactDB"
                    ).addMigrations(migration_1_2).build()
                }
            }
            return INSTANCE!!
        }

    }

}