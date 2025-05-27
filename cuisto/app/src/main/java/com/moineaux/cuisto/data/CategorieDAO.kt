package com.moineaux.cuisto.data

import androidx.room.*

@Dao
interface CategorieDAO {
    @Insert suspend fun sauvegarderCategorie(categorie: Categorie): Long
}