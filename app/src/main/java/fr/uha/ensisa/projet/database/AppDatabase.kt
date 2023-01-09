package fr.uha.ensisa.projet.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

public abstract class AppDatabase : RoomDatabase() {
    companion object {
        private lateinit var instance : AppDatabase

        @Synchronized
        fun create (context: Context) : AppDatabase {
            instance = Room.databaseBuilder(context, AppDatabase::class.java, "coaching.db").build()
            return instance
        }

        @Synchronized
        fun get () : AppDatabase {
            return instance
        }
    }
}