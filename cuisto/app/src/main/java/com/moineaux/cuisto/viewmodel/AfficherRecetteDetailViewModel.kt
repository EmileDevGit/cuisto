package com.moineaux.cuisto.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.moineaux.cuisto.data.AppDatabase
import com.moineaux.cuisto.data.Etape
import kotlinx.coroutines.launch

class AfficherRecetteDetailViewModel(application: Application): AndroidViewModel(application) {

    private val _ingredients = MutableLiveData<String>()
    val ingredientsRecetteDetail : LiveData<String> = _ingredients

    private val _etapes = MutableLiveData<List<Etape>>(emptyList())
    val listEtapeRecetteDetail : LiveData<List<Etape>> = _etapes

    private val _nomRecette = MutableLiveData<String>()
    val nomRecetteDetail: LiveData<String> = _nomRecette

    private val _categorieRecette = MutableLiveData<String>()
    val categorieRecetteDetail: LiveData<String> = _categorieRecette

    private val _imageUri = MutableLiveData<String>()
    val imageUri: LiveData<String> = _imageUri

    private val _isMock = MutableLiveData<Int>()
    val isMock: LiveData<Int> get() = _isMock

    private val recetteDao = AppDatabase.getDatabase(application).recetteDao()
    private val categorieDAO = AppDatabase.getDatabase(application).categorieDao()
    private val etapeDAO = AppDatabase.getDatabase(application).etapeDao()

    fun afficherRecette(idRecette: Long) = viewModelScope.launch {
        // Fonction qui cherche la recette dans bd
        val recette = recetteDao.recupererRecetteAvecId(idRecette)
        val categorie = categorieDAO.getCategorieAvecId(recette.categorieId)
        val listEtapes = etapeDAO.recupererlisteEtapesAvecIdRecette(idRecette)


        _etapes.value = listEtapes
        _nomRecette.value = recette.nomRecette
        _categorieRecette.value = categorie.nom
        _ingredients.value = recette.ingredients
        _imageUri.value = recette.image
        _isMock.value = recette.isMock
    }

    fun supprimerRecette(idRecette: Long) = viewModelScope.launch {
        recetteDao.supprimerRecetteAvecId(idRecette)
        etapeDAO.supprimerEtapesAvecId(idRecette)
    }
}