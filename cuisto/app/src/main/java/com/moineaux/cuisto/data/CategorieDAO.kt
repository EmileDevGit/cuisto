package com.moineaux.cuisto.data

import androidx.room.*

@Dao
interface CategorieDAO {
    @Insert suspend fun sauvegarderCategorie(categorie: Categorie): Long
    @Insert suspend fun insert(categorie: Categorie): Long
    @Query("DELETE FROM Categorie") fun supprimerToutesLesCategories()

}