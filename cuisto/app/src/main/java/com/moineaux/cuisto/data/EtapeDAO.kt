package com.moineaux.cuisto.data

import androidx.room.*

@Dao
interface EtapeDAO {
    @Query("SELECT * FROM Etape WHERE recetteId = :idRecette ORDER BY ordre ASC")
    suspend fun recupererlisteEtapesAvecIdRecette(idRecette: Long): List<Etape>

    @Query("DELETE FROM Etape WHERE recetteId = :idDuRecette")
    suspend fun supprimerEtapesAvecId(idDuRecette: Long)
}