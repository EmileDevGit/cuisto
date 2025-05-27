package com.moineaux.cuisto.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Ingredient (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nom: String
)