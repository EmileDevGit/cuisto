package com.moineaux.cuisto.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object MockData {
    fun insererDonneesMock(db: AppDatabase) {
        CoroutineScope(Dispatchers.IO).launch {

            // ********************** Si vide il remplie avec les donnees du Mock ***********************
            if (db.recetteDao().toutRecuperer().isEmpty()) {

                val pizzaId = db.categorieDao().insert(Categorie(nom = "Pizza"))
                val saladeId = db.categorieDao().insert(Categorie(nom = "Salade"))
                val burgerId = db.categorieDao().insert(Categorie(nom = "Burger"))
                val patesId = db.categorieDao().insert(Categorie(nom = "Pâtes"))

                db.recetteDao().insertRecetteAvecEtapes(
                    Recette(nomRecette = "Pizza chèvre miel noix", image = "pizzachevremiel", ingredients = "Chèvre, miel, noix", categorieId = pizzaId),
                    listOf("Préchauffer le four", "Étaler la pâte", "Ajouter les ingrédients", "Cuire 15 min")
                )

                db.recetteDao().insertRecetteAvecEtapes(
                    Recette(nomRecette = "Salade de quinoa", image = "saladequinoa", ingredients = "Quinoa, légumes", categorieId = saladeId),
                    listOf("Cuire le quinoa", "Couper les légumes", "Mélanger", "Assaisonner")
                )

                db.recetteDao().insertRecetteAvecEtapes(
                    Recette(nomRecette = "Burger steak fromage", image = "burgersteakfromage", ingredients = "Steak, fromage, pain", categorieId = burgerId),
                    listOf("Cuire le steak", "Griller le pain", "Monter le burger", "Servir chaud")
                )

                db.recetteDao().insertRecetteAvecEtapes(
                    Recette(nomRecette = "Pâtes aux champignons", image = "pateschampignons", ingredients = "Pâtes, champignons, crème", categorieId = patesId),
                    listOf("Faire cuire les pâtes", "Sauter les champignons", "Ajouter la crème", "Mélanger le tout")
                )
            }
        }
    }
}