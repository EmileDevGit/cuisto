package com.moineaux.cuisto.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Recette::class,
        parentColumns = ["id"],
        childColumns = ["recetteId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Etape(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val recetteId: Long,
    val ordre: Int,
    val description: String
) {
}