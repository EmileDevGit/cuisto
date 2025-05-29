package com.moineaux.cuisto.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object MockData {
    suspend fun insererDonneesMock(db: AppDatabase) {
        withContext(Dispatchers.IO) {
            if (db.recetteDao().toutRecuperer().isEmpty()) {

                val pizzaId = db.categorieDao().insert(Categorie(nom = "Pizza", image = "icone_pizza_cat"))
                val saladeId = db.categorieDao().insert(Categorie(nom = "Salade", image = "icone_salade_cat"))
                val burgerId = db.categorieDao().insert(Categorie(nom = "Burger", image = "icone_burger_cat"))
                val patesId = db.categorieDao().insert(Categorie(nom = "Pâtes", image = "icone_pates_cat"))

                db.recetteDao().insertRecetteAvecEtapes(
                    Recette(nomRecette = "Pizza chèvre miel noix", image = "pizzachevremiel", ingredients = "Chèvre, miel, noix", categorieId = pizzaId,isMock = 1),
                    listOf(
                        Etape(recetteId = 1, ordre = 1, description = "Préchauffer le four"),
                        Etape(recetteId = 1, ordre = 2, description = "Étaler la pâte"),
                        Etape(recetteId = 1, ordre = 3, description ="Ajouter les ingrédients"),
                        Etape(recetteId = 1, ordre = 4, description = "Cuire 15 min")
                    )
                )

                db.recetteDao().insertRecetteAvecEtapes(
                    Recette(nomRecette = "Salade de quinoa", image = "saladequinoa", ingredients = "Quinoa, légumes", categorieId = saladeId,isMock = 1),
                    listOf(
                        Etape(recetteId = 1, ordre = 1, description = "Préchauffer le four"),
                        Etape(recetteId = 1, ordre = 2, description = "Couper les légumes"),
                        Etape(recetteId = 1, ordre = 3, description = "Mélanger"),
                        Etape(recetteId = 1, ordre = 4, description = "Assaisonner")
                    )
                )

                db.recetteDao().insertRecetteAvecEtapes(
                    Recette(nomRecette = "Burger steak fromage", image = "burgersteakfromage", ingredients = "Steak, fromage, pain", categorieId = burgerId,isMock = 1),
                    listOf(
                        Etape(recetteId = 1, ordre = 1, description = "Cuire le steak"),
                        Etape(recetteId = 1, ordre = 2, description = "Griller le pain"),
                        Etape(recetteId = 1, ordre = 3, description = "Monter le burger"),
                        Etape(recetteId = 1, ordre = 4, description = "Servir chaud")
                    )
                )

                db.recetteDao().insertRecetteAvecEtapes(
                    Recette(nomRecette = "Pâtes aux champignons", image = "pateschampignons", ingredients = "Pâtes, champignons, crème", categorieId = patesId,isMock = 1),
                    listOf(
                        Etape(recetteId = 1, ordre = 1, description = "Faire cuire les pâtes"),
                        Etape(recetteId = 1, ordre = 2, description ="Sauter les champignons"),
                        Etape(recetteId = 1, ordre = 3, description = "Ajouter la crème"),
                        Etape(recetteId = 1, ordre = 4, description = "Mélanger le tout")
                    )
                )

            }
        }
    }
}
