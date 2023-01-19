package fr.uha.gm.projet.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fr.uha.gm.projet.model.Conseil
import fr.uha.gm.projet.model.Jour
import fr.uha.gm.projet.model.JourConseilsAssociation

@Database(entities = [Conseil::class, Jour::class, JourConseilsAssociation::class], version = 1, exportSchema = false)
//@TypeConverters(DatabaseConverters::class)
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

    abstract fun getConseilDao () : ConseilDao
    abstract fun getJourDao () : JourDao
}