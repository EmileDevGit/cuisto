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
        val listeEtapesActuelle = _etapes.value.orEmpty().toMutableList()

        val nouvelleEtape = Etape(
            id = idCompteur++, // <-- Génère un ID unique pour la gestion dans la vue, sera override par la base de données
            recetteId = 0,
            ordre = listeEtapesActuelle.size,
            description = ""
        )

        listeEtapesActuelle.add(nouvelleEtape)
        _etapes.value = listeEtapesActuelle
    }

    fun supprimerEtape(position: Int) {
        val listeEtapesActuelle = _etapes.value.orEmpty().toMutableList()
        if (position in listeEtapesActuelle.indices) {
            listeEtapesActuelle.removeAt(position)
            _etapes.value = listeEtapesActuelle
        }
    }

    fun deplacerEtape(positionInitiale: Int, positionFinale: Int) {
        val listeEtapesActuelle = _etapes.value.orEmpty().toMutableList()
        if (positionInitiale in listeEtapesActuelle.indices && positionFinale in listeEtapesActuelle.indices) {
            val item = listeEtapesActuelle.removeAt(positionInitiale)
            listeEtapesActuelle.add(positionFinale, item)
            _etapes.value = listeEtapesActuelle
        }
    }
}