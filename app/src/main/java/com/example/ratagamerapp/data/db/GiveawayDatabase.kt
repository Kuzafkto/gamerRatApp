package com.example.ratagamerapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.security.AccessControlContext


@Database(entities = [GiveawayEntity::class,CommentEntity::class], version = 1)
abstract class GiveawayDatabase() : RoomDatabase() {

    abstract fun giveawayDao(): GiveawayDao
    companion object {
        @Volatile
        private var INSTANCE: GiveawayDatabase? = null

        fun getInstance(context: Context): GiveawayDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): GiveawayDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                GiveawayDatabase::class.java,
                "giveaway_db"
            ).build()
        }
    }
}