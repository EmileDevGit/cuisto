package com.moineaux.cuisto.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RecetteDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun sauvegarderRecette(recette: Recette): Long

    @Insert suspend fun sauvegarderListeEtapes(etapes: List<Etape>)

    @Query("SELECT * FROM Recette")
    suspend fun toutRecuperer(): List<Recette>

    @Query("SELECT * FROM Recette WHERE id = :idRecette")
    suspend fun recupererRecetteAvecId(idRecette: Long): Recette

    @Query("SELECT * FROM Recette WHERE nomRecette  = :nomDuRecette")
    suspend fun recupererRecetteAvecNom(nomDuRecette: String?): List<Recette>


    @Query("DELETE FROM Recette")
    fun supprimerToutesLesRecettes()

    // ******** Affiche les 4 premiers *********

    @Query("SELECT * FROM Recette WHERE isMock = 1")
    suspend fun recupererRecettesMock(): List<Recette>

    // Cette fonction peut être suspendue (pause) et reprise plus tard. Elle doit être appelée dans une coroutine (asynchrone)
    @Delete
    suspend fun supprimer(recette: Recette)

    @Query("SELECT * FROM Recette WHERE categorieId = :idCategorie")
    fun getRecettesParCategorie(idCategorie: Long): LiveData<List<Recette>>
    @Query("SELECT * FROM Recette WHERE isMock = 0")
    suspend fun getRecettesNonMock(): List<Recette>

    @Transaction
    suspend fun insertRecetteAvecEtapes(recette: Recette, listeEtapes: List<Etape>) {
        val recetteId = sauvegarderRecette(recette) // ID auto-généré de la recette

        val etapesAvecId = listeEtapes.mapIndexed { index, etape ->
            etape.copy(recetteId = recetteId, ordre = index)
        }

        sauvegarderListeEtapes(etapesAvecId)
    }

    @Query("SELECT * FROM Recette WHERE nomRecette = :nom")
    suspend fun recetteParNom(nom: String): Recette?

    @Query("DELETE FROM Recette WHERE id = :idRecette")
    suspend fun supprimerRecetteAvecId(idRecette: Long)

    @Transaction
    suspend fun updateRecetteAvecEtapes(recette: Recette, nouvellesEtapes: List<Etape>) {
        update(recette)
        supprimerEtapesParRecette(recette.id)

        val etapesPrêtes = nouvellesEtapes.mapIndexed { index, etape ->
            etape.copy(
                ordre = index,
                recetteId = recette.id
            )
        }

        insererEtapes(etapesPrêtes)
    }
    @Update
    suspend fun update(recette: Recette)

    @Query("DELETE FROM Etape WHERE recetteId = :recetteId")
    suspend fun supprimerEtapesParRecette(recetteId: Long)

    @Insert
    suspend fun insererEtapes(etapes: List<Etape>)




}