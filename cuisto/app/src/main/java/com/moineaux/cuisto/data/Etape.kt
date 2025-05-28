package com.moineaux.cuisto.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
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
    var recetteId: Long,
    val ordre: Int,
    val description: String,
    @Ignore var tempid: Long = 0
) {
    constructor(id: Long, recetteId: Long, ordre: Int, description: String)
            : this(id, recetteId, ordre, description, 0)
}