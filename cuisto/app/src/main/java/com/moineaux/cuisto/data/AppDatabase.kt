package com.moineaux.cuisto.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Recette::class, Etape::class, Categorie::class], version = 33)
abstract class AppDatabase : RoomDatabase() {

    abstract fun recetteDao(): RecetteDAO
    abstract fun categorieDao(): CategorieDAO
    abstract fun etapeDao(): EtapeDAO


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