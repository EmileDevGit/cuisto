package com.moineaux.cuisto.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


// ********* Clé étrangère de Categorie ******
@Entity(
    foreignKeys = [ForeignKey(
        entity = Categorie::class,
        parentColumns = ["id"],
        childColumns = ["categorieId"],
        onDelete = ForeignKey.CASCADE
    )]
)
// ************

data class Recette(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nomRecette: String,
    val image: String,
    val ingredients: String,
    val categorieId: Long  //  Clé étrangère vers Categorie.id
)