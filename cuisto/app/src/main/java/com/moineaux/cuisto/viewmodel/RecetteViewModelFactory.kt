
package com.moineaux.cuisto.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

//  ********************* Sert à créer un ViewModel personnalisé *************************

class RecetteViewModelFactory( private val application: Application,

// ************ Hérite de AndroidViewModelFactory pour profiter des outils de création de ViewModel avec Application *************
) : ViewModelProvider.AndroidViewModelFactory(application) {

    // Cette méthode est appelée automatiquement par Android quand on veut créer un ViewModel
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        // Vérifie si on demande un ViewModel du type RecetteViewModel
        if (modelClass.isAssignableFrom(RecetteViewModel::class.java)) {

            // Supprime l'avertissement du cast (conversion de type)
            @Suppress("UNCHECKED_CAST")

            // Retourne une instance de RecetteViewModel créée avec l'application
            return RecetteViewModel(application) as T
        }

        // Si on demande un autre type de ViewModel, on signale une erreur
        throw IllegalArgumentException("Classe ViewModel inconnue")
    }
}
