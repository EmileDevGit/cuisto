package com.moineaux.cuisto.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
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

    private val repository = RecetteRepository(AppDatabase.getDatabase(application))

    // ******** Recettes regroupées par catégorie *************

    private val _recettesParCategorie = MutableStateFlow<Map<String, List<Recette>>>(emptyMap())
    val recettesParCategorie: StateFlow<Map<String, List<Recette>>> = _recettesParCategorie

    // ******** Chargement des recettes regroupées par nom de catégorie ************

    fun chargerRecettes() {
        viewModelScope.launch {
            val toutesRecettes = repository.recupererToutesLesRecettes()
            val regroupées = withContext(Dispatchers.IO) {
                toutesRecettes.groupBy {
                    repository.db.categorieDao().getNomCategorieParId(it.categorieId) ?: "Autre"
                }
            }
            _recettesParCategorie.emit(regroupées)
        }
    }

    // *********** Insère les données mock uniquement si recettes ET catégories sont vides *************

    fun initialiserMockSiVide(onDone: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val recettes = repository.recupererToutesLesRecettes()
            val categories = repository.db.categorieDao().getToutesLesCategories()
            if (recettes.isEmpty() && categories.isEmpty()) {
                MockData.insererDonneesMock(repository.db)
            }
            withContext(Dispatchers.Main) {
                onDone()
            }
        }
    }

    // *********** Force l’insertion des données mock (à utiliser avec prudence si déjà appelées) *****************

    fun insererDonneesMock() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insererDonneesMock()
        }
    }

    // *********** Récupère uniquement les recettes marquées comme "mock" *****************

    fun chargerRecettesMock(onResult: (List<Recette>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val recettes = repository.recupererRecettesMock()
            withContext(Dispatchers.Main) {
                onResult(recettes)
            }
        }
    }

    fun chargerToutesRecettes(onResult: (List<Recette>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val recettes = repository.recupererToutesLesRecettes()
            withContext(Dispatchers.Main) {
                onResult(recettes)
            }
        }
    }

    fun getRecettesParCategorie(idCategorie: Long): LiveData<List<Recette>> {
        return repository.getRecettesParCategorie(idCategorie)
    }
}
