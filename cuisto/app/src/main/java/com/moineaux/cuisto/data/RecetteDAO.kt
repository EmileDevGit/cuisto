package com.moineaux.cuisto.data

import androidx.room.*

@Dao
interface RecetteDAO {
    @Insert suspend fun insertRecette(recette: Recette): Long
    @Insert suspend fun insertEtapes(etapes: List<Etape>)

    @Transaction
    suspend fun insertRecetteAvecEtapes(recette: Recette, descriptions: List<String>) {
        val recetteId = insertRecette(recette)
        val etapes = descriptions.mapIndexed { index, desc ->
            Etape(recetteId = recetteId, ordre = index, description = desc)
        }
        insertEtapes(etapes)
    }
}