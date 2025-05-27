package com.moineaux.cuisto.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moineaux.cuisto.R
import com.moineaux.cuisto.data.Etape
import com.moineaux.cuisto.data.Ingredient
import androidx.core.net.toUri

class AfficherRecetteDetailViewModel: ViewModel() {
    private val _ingredients = MutableLiveData<List<Ingredient>>(emptyList())
    val listIngredientRecetteDetail : LiveData<List<Ingredient>> = _ingredients

    private val _etapes = MutableLiveData<List<Etape>>(emptyList())
    val listEtapeRecetteDetail : LiveData<List<Etape>> = _etapes

    private val _nomRecette = MutableLiveData<String>()
    val nomRecetteDetail: LiveData<String> = _nomRecette

    private val _categorieRecette = MutableLiveData<String>()
    val categorieRecetteDetail: LiveData<String> = _categorieRecette

    private val _imageUri = MutableLiveData<String>()
    val imageUri: LiveData<String> = _imageUri

    fun afficherRecette(idRecette: Int) {

    }

//    private fun afficherMock1() {
//        // Modele 1 recette - Salade de quinoa
//        val listIngredient = listOf(
//            Ingredient(0, "200 g de quinoa"),
//            Ingredient(0, "1 concombre"),
//            Ingredient(0, "Jus d'un citron")
//        )
//        val listEtape = listOf(
//            Etape(0, 0, 1, "Cuire le quinoa et laisser refroidir."),
//            Etape(0, 0, 2, "Couper le concombre en dés."),
//            Etape(0, 0, 3, "Mélanger le quinoa, le concombre et assaisonner avec le jus de citron, sel et poivre.")
//        )
//
//        _etapes.value = listEtape
//        _ingredients.value = listIngredient
//
//        _nomRecette.value = "Salade de quinoa"
//        _categorieRecette.value = "Salade"
//
//        val uri = ("android.resource://com.moineaux.cuisto/" + R.drawable.saladequinoa).toUri()
//        _imageUri.value = uri.toString()
//    }
//
//    private fun afficherMock2() {
//        // Modele 2 recette - Pizza Chevre miel noix
//        val listIngredient = listOf(
//            Ingredient(0, "1 pâte à pizza"),
//            Ingredient(0, "200 g de fromage de chèvre"),
//            Ingredient(0, "2 c. à soupe de miel")
//        )
//        val listEtape = listOf(
//            Etape(0, 0, 1, "Préchauffer le four à 180°C et étaler la pâte sur une plaque."),
//            Etape(0, 0, 2, "Disposer le fromage de chèvre sur la pâte."),
//            Etape(0, 0, 3, "Arroser de miel et enfourner 20 à 30 minutes.")
//        )
//
//        _etapes.value = listEtape
//        _ingredients.value = listIngredient
//
//        _nomRecette.value = "Pizza chèvre miel noix"
//        _categorieRecette.value = "Pizza"
//
//        val uri = ("android.resource://com.moineaux.cuisto/" + R.drawable.pizzachevremiel).toUri()
//        _imageUri.value = uri.toString()
//    }
//
//    private fun afficherMock3() {
//        // Modele 3 recette - Burger steak fromage
//        val listIngredient = listOf(
//            Ingredient(0, "1 pain à burger"),
//            Ingredient(0, "1 steak haché"),
//            Ingredient(0, "1 tranche de fromage")
//        )
//
//        val listEtape = listOf(
//            Etape(0, 0, 1, "Assaisonnez et façonnez le steak haché."),
//            Etape(0, 0, 2, "Faites cuire le steak dans une poêle et déposez la tranche de fromage en fin de cuisson."),
//            Etape(0, 0, 3, "Grillez le pain à burger et assemblez le burger avec le steak chaud et le fromage fondu.")
//        )
//
//        _ingredients.value = listIngredient
//        _etapes.value = listEtape
//
//        _nomRecette.value = "Burger steak fromage"
//        _categorieRecette.value = "Burger"
//
//        val uri = ("android.resource://com.moineaux.cuisto/" + R.drawable.burgersteakfromage).toUri()
//        _imageUri.value = uri.toString()
//
//    }
//
//    private fun afficherMock4() {
//        // Modele 4 recette - Pâtes aux champignons
//        val listIngredient = listOf(
//            Ingredient(0, "250 g de spaghetti"),
//            Ingredient(0, "300 g de champignons de Paris"),
//            Ingredient(0, "100 ml de crème fraîche")
//        )
//
//        val listEtape = listOf(
//            Etape(0, 0, 1, "Faire cuire les spaghetti dans de l'eau bouillante salée."),
//            Etape(0, 0, 2, "Faire revenir les champignons émincés dans une poêle avec un peu d'huile."),
//            Etape(0, 0, 3, "Mélanger les pâtes cuites avec les champignons et la crème fraîche. Assaisonner au goût.")
//        )
//
//        _ingredients.value = listIngredient
//        _etapes.value = listEtape
//
//        _nomRecette.value = "Pâtes aux champignons"
//        _categorieRecette.value = "Pâtes"
//
//        val uri = ("android.resource://com.moineaux.cuisto/" + R.drawable.pateschampignons).toUri()
//        _imageUri.value = uri.toString()
//
//    }
}