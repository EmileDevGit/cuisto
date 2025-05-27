package com.moineaux.cuisto.data

import androidx.room.*

@Dao
interface RecetteDAO {
    @Insert suspend fun sauvegarderRecette(recette: Recette): Long
    @Insert suspend fun sauvegarderListeEtapes(etapes: List<Etape>)
    @Query("SELECT * FROM Recette")
    suspend fun toutRecuperer(): List<Recette>
    @Query("DELETE FROM Recette")
    fun supprimerToutesLesRecettes()


    @Transaction
    suspend fun insertRecetteAvecEtapes(recette: Recette, listeEtapes: List<String>) {
        val recetteId = sauvegarderRecette(recette)


        // À retravailler
        val etapes = listeEtapes.mapIndexed { index, desc ->
            Etape(recetteId = recetteId, ordre = index, description = desc)
        }
        sauvegarderListeEtapes(etapes)
    }
}