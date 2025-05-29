package com.moineaux.cuisto.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.moineaux.cuisto.data.AppDatabase
import com.moineaux.cuisto.data.MockData
import com.moineaux.cuisto.data.Recette
import com.moineaux.cuisto.repository.RecetteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecetteViewModel(application: Application) : AndroidViewModel(application) {

    //  ********************* Repository avec accès à la base de données *********************

    private val repository = RecetteRepository(AppDatabase.getDatabase(application))

    // ******************* Stocke les recettes regroupées par catégorie *********************

    private val _recettesParCategorie = MutableStateFlow<Map<String, List<Recette>>>(emptyMap())
    val recettesParCategorie: StateFlow<Map<String, List<Recette>>> = _recettesParCategorie

    // **************** Prend toutes les recettes, les organise par type (comme "Entrées", "Desserts"...) **************

    fun chargerRecettes(filtre: String? = null) {
        viewModelScope.launch {
            val toutesRecettes = repository.recupererToutesLesRecettes()
            val recettesAfficher = if (!filtre.isNullOrBlank()) {
                toutesRecettes.filter { recette ->
                    recette.nomRecette.contains(filtre, ignoreCase = true)
                }
            } else {
                toutesRecettes
            }

            val regroupées = withContext(Dispatchers.IO) {
                recettesAfficher.groupBy { recette ->
                    repository.db.categorieDao().getNomCategorieParId(recette.categorieId) ?: "Autre"
                }
            }

            _recettesParCategorie.emit(regroupées)
        }
    }

    // ******************** Insère les recettes mock (fictives) *****************************

    fun insererDonneesMock() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insererDonneesMock()
        }
    }

    // ********************* Insère les recettes mock uniquement si la base est vide **********************

    fun initialiserMockSiVide() {
        viewModelScope.launch(Dispatchers.IO) {
            val vide = repository.recupererToutesLesRecettes().isEmpty()
            if (vide) {
                MockData.insererDonneesMock(repository.db)
            }
        }
    }

    // Récupère les recettes mock et appelle un callback avec le résultat - Unit : fonction qui retourne rien
    fun chargerRecettesMock(onResult: (List<Recette>) -> Unit) {
//        On lance une coroutine pour faire le travail en arrière-plan, sans bloquer l'application
        viewModelScope.launch(Dispatchers.IO) {
//            On demande au repository de nous donner les recettes fictives (mock)
            val recettes = repository.recupererRecettesMock()
//            On utilise le callback (la fonction passée en paramètre) pour envoyer les recettes à l’endroit qui a appelé cette fonction
            onResult(recettes)
        }
    }
}
