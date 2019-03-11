package com.nitish.weatherapp.database

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database

@Database(entities = [TempResponse::class,TminResponse::class, RainfallResponse::class], version = 2)
abstract class TempRoomDatabase : RoomDatabase() {

    abstract fun tmaxDao(): TmaxDao
    abstract fun tminDao(): TminDao
    abstract fun rainfallDao(): RainfallDao

    /*companion object {

        @Volatile
        private var INSTANCE: TempRoomDatabase? = null

        internal fun getDatabase(context: Context): TempRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(TempRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            TempRoomDatabase::class.java, "tmax_database"
                        )
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }*/
}