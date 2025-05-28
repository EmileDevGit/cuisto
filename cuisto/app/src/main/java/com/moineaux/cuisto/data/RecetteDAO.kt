package com.moineaux.cuisto.data

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

    // « Cette fonction peut être suspendue (pause) et reprise plus tard. Elle doit être appelée dans une coroutine (asynchrone). »
    @Delete
    suspend fun supprimer(recette: Recette)

    // A UTILISER SUR LE BOUTON POUR SUPPRIMER
//    if (!recette.isMock) {
//        db.recetteDao().supprimer(recette)
//    } else {
//        Toast.makeText(context, "Impossible de supprimer cette recette par défaut.", Toast.LENGTH_SHORT).show()
//    }


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

}