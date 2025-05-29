package com.moineaux.cuisto.data

import androidx.room.*

@Dao
interface CategorieDAO {
    @Insert suspend fun sauvegarderCategorie(categorie: Categorie): Long
    @Insert suspend fun insert(categorie: Categorie): Long
    @Query("DELETE FROM Categorie") fun supprimerToutesLesCategories()

    // **************  PERMET DE VERIFIER SI LA CATEGORIE EXISTE ***************

    @Query("SELECT * FROM Categorie WHERE nom = :nom LIMIT 1")
    suspend fun getCategorieParNom(nom: String): Categorie?

    @Query("SELECT nom FROM categorie WHERE id = :id")
    suspend fun getNomParId(id: Long): String

    @Query("SELECT nom FROM categorie WHERE id = :id LIMIT 1")
    suspend fun getNomCategorieParId(id: Long): String?

    @Query("SELECT * FROM Categorie")
    suspend fun getToutesLesCategories(): List<Categorie>



}