package com.moineaux.cuisto.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
                    Recette(nomRecette = "Pizza chèvre miel noix", image = "pizzachevremiel", ingredients = "Chèvre, miel, noix", categorieId = pizzaId, isMock = 1),
                    listOf("Préchauffer le four", "Étaler la pâte", "Ajouter les ingrédients", "Cuire 15 min")
                )
                db.recetteDao().insertRecetteAvecEtapes(
                    Recette(nomRecette = "Salade de quinoa", image = "saladequinoa", ingredients = "Quinoa, légumes", categorieId = saladeId, isMock = 1),
                    listOf("Cuire le quinoa", "Couper les légumes", "Mélanger", "Assaisonner")
                )
                db.recetteDao().insertRecetteAvecEtapes(
                    Recette(nomRecette = "Burger steak fromage", image = "burgersteakfromage", ingredients = "Steak, fromage, pain", categorieId = burgerId, isMock = 1),
                    listOf("Cuire le steak", "Griller le pain", "Monter le burger", "Servir chaud")
                )
                db.recetteDao().insertRecetteAvecEtapes(
                    Recette(nomRecette = "Pâtes aux champignons", image = "pateschampignons", ingredients = "Pâtes, champignons, crème", categorieId = patesId, isMock = 1),
                    listOf("Faire cuire les pâtes", "Sauter les champignons", "Ajouter la crème", "Mélanger le tout")
                )
            }
        }
    }
}
