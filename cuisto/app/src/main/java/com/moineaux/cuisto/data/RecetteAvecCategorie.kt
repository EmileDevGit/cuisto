package com.moineaux.cuisto.data

import androidx.room.Embedded
import androidx.room.Relation

data class RecetteAvecCategorie(
    @Embedded val recette: Recette,
    @Relation(
        parentColumn = "categorieId",
        entityColumn = "id"
    )
    val categorie: Categorie
)
