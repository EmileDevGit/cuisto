package com.moineaux.cuisto.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class Categorie(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    val nom: String,
    val image: String
)