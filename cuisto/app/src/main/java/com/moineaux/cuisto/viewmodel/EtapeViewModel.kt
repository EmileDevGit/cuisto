package com.moineaux.cuisto.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moineaux.cuisto.data.Etape

class EtapeViewModel : ViewModel() {

    private val _etapes = MutableLiveData<List<Etape>>(emptyList())
    val listeEtapes: LiveData<List<Etape>> = _etapes

    private var idCompteur = 1L

    fun ajouterEtape() {
        val currentList = _etapes.value.orEmpty().toMutableList()

        val nouvelleEtape = Etape(
            id = idCompteur++,
            recetteId = 0,
            ordre = currentList.size,
            description = ""
        )

        currentList.add(nouvelleEtape)
        _etapes.value = currentList
    }

    fun supprimerEtape(position: Int) {
        val currentList = _etapes.value.orEmpty().toMutableList()
        if (position in currentList.indices) {
            currentList.removeAt(position)
            _etapes.value = currentList
        }
    }

    fun deplacerEtape(fromPosition: Int, toPosition: Int) {
        val currentList = _etapes.value.orEmpty().toMutableList()
        if (fromPosition in currentList.indices && toPosition in currentList.indices) {
            val item = currentList.removeAt(fromPosition)
            currentList.add(toPosition, item)
            _etapes.value = currentList
        }
    }
}