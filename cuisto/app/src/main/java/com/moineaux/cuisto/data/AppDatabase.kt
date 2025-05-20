package com.moineaux.cuisto.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// REMPLACER Article::class PAR LE NOM DE VOTRE CLASSE
@Database(entities = [Article::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    // REMPLACER articleDao() PAR LE NOM DE VOTRE INTERFACE
    abstract fun articleDao(): ArticleDAO

    companion object {
        @Volatile
        private var INSTANCE : AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            return INSTANCE ?: synchronized(this){
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cuistodb"
                )
                    // ONLY IN LOCAL - Drop all tables on schema change
                .fallbackToDestructiveMigration(true)
                    // ONLY IN LOCAL

                .build().also {INSTANCE = it}
            }
        }
    }


}