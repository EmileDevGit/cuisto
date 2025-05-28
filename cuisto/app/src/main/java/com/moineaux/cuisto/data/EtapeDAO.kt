package com.moineaux.cuisto.data

import androidx.room.*

@Dao
interface EtapeDAO {
    @Query("SELECT * FROM Etape WHERE recetteId = :idDuRecette")
    suspend fun recupererlisteEtapesAvecId(idDuRecette: Long): List<Etape>
}