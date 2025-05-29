package com.moineaux.cuisto.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RecetteDAO {

    // ----------------- INSERTIONS -----------------

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun sauvegarderRecette(recette: Recette): Long

    @Insert
    suspend fun sauvegarderListeEtapes(etapes: List<Etape>)

    @Insert
    suspend fun insererEtapes(etapes: List<Etape>)

    // Insertion combinée d’une recette avec ses étapes
    @Transaction
    suspend fun insertRecetteAvecEtapes(recette: Recette, listeEtapes: List<Etape>) {
        val recetteId = sauvegarderRecette(recette)

        val etapesAvecId = listeEtapes.mapIndexed { index, etape ->
            etape.copy(recetteId = recetteId, ordre = index)
        }

        sauvegarderListeEtapes(etapesAvecId)
    }

    // ----------------- MISE À JOUR -----------------

    @Update
    suspend fun update(recette: Recette)

    @Transaction
    suspend fun updateRecetteAvecEtapes(recette: Recette, nouvellesEtapes: List<Etape>) {
        update(recette)
        supprimerEtapesParRecette(recette.id)

        val etapesPrêtes = nouvellesEtapes.mapIndexed { index, etape ->
            etape.copy(ordre = index, recetteId = recette.id)
        }

        insererEtapes(etapesPrêtes)
    }

    // ----------------- RÉCUPÉRATIONS -----------------

    @Query("SELECT * FROM Recette")
    suspend fun toutRecuperer(): List<Recette>

    @Query("SELECT * FROM Recette WHERE id = :idRecette")
    suspend fun recupererRecetteAvecId(idRecette: Long): Recette

    @Query("SELECT * FROM Recette WHERE nomRecette = :nomDuRecette")
    suspend fun recupererRecetteAvecNom(nomDuRecette: String?): List<Recette>

    @Query("SELECT * FROM Recette WHERE nomRecette = :nom")
    suspend fun recetteParNom(nom: String): Recette?

    @Query("SELECT * FROM Recette WHERE categorieId = :idCategorie")
    fun getRecettesParCategorie(idCategorie: Long): LiveData<List<Recette>>

    @Query("SELECT * FROM Recette WHERE isMock = 1")
    suspend fun recupererRecettesMock(): List<Recette>

    @Query("SELECT * FROM Recette WHERE isMock = 0")
    suspend fun getRecettesNonMock(): List<Recette>

    // ----------------- SUPPRESSIONS -----------------

    @Delete
    suspend fun supprimer(recette: Recette)

    @Query("DELETE FROM Recette")
    fun supprimerToutesLesRecettes()

    @Query("DELETE FROM Recette WHERE id = :idRecette")
    suspend fun supprimerRecetteAvecId(idRecette: Long)

    @Query("DELETE FROM Etape WHERE recetteId = :recetteId")
    suspend fun supprimerEtapesParRecette(recetteId: Long)
}