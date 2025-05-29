package com.moineaux.cuisto.ui

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moineaux.cuisto.data.AppDatabase
import com.moineaux.cuisto.data.Categorie
import com.moineaux.cuisto.data.Etape
import com.moineaux.cuisto.data.Recette
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class AjouterRecetteViewModel(application: Application) : AndroidViewModel(application) {

    // INIT
    private val db = AppDatabase.getDatabase(application)
    private val _nomCategories = MutableLiveData<List<String>>()
    val categories: LiveData<List<String>> = _nomCategories

    private val _fermerActivity = MutableLiveData<Boolean>(false)
    val fermerActivity: LiveData<Boolean> get() = _fermerActivity

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> get() = _message

    init {
        viewModelScope.launch {
            val categoriesDansDb = withContext(Dispatchers.IO) {
                db.categorieDao().getAllNomsCategories()
            }
            _nomCategories.value = categoriesDansDb
        }
    }


    // Méthodes pour gérer l'image
    private val _imageUri = MutableLiveData<Uri?>()
    val imageUri: LiveData<Uri?> = _imageUri

    fun definirImage(uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            val context = getApplication<Application>().applicationContext
            try {
                val inputStream = context.contentResolver.openInputStream(uri)
                val fileName = "recette_image_${System.currentTimeMillis()}.jpg"
                val imageFile = File(context.filesDir, fileName)

                inputStream?.use { input ->
                    FileOutputStream(imageFile).use { output ->
                        input.copyTo(output)
                    }
                }

                val localUri = Uri.fromFile(imageFile)
                withContext(Dispatchers.Main) {
                    _imageUri.value = localUri
                }

            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    _message.value = "Erreur lors de la copie de l'image"
                }
            }
        }
    }
//    fun reinitialiserImage() {
//        _imageUri.value = null
//    }



    // Méthodes pour gérer les étapes
    private val _etapes = MutableLiveData<List<Etape>>(emptyList())
    val listeEtapes: LiveData<List<Etape>> = _etapes
    private var idCompteur = 1L

    fun ajouterEtape() {
        val listeEtapesActuelle = _etapes.value.orEmpty().toMutableList()

        val nouvelleEtape = Etape(
            tempid = idCompteur++, //<-- ID permettant de gérer l'ordre des étapes sur le drag n drop
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

    fun mettreAJourEtapes(nouvellesEtapes: List<Etape>) {
        _etapes.value = nouvellesEtapes
    }




    fun enregistrerRecette(nom: String, imageUri: Uri?, ingredients: String, categorieNom: String){

        // Validation des étapes
        val etapes = _etapes.value
        if (nom.isBlank() || ingredients.isBlank() || categorieNom.isBlank() || imageUri == null) {
            _message.value = "Veuillez remplir tous les champs"
            return
        }

        if (etapes.isNullOrEmpty()) {
            _message.value = "Veuillez ajouter au moins une étape"
            return
        }

        if (etapes.any { it.description.isBlank() }) {
            _message.value = "Veuillez retirer les étapes vides"
            return
        }

        // Coroutine de validation recette existante et enregistrement
        viewModelScope.launch {
            val recetteExistante = withContext(Dispatchers.IO) {
                db.recetteDao().recetteParNom(nom)
            }
            if (recetteExistante != null) {
                _message.postValue("Une recette avec ce nom existe déjà.")
                return@launch
            }

            val categorieId = withContext(Dispatchers.IO) {
                val categorie = db.categorieDao().getCategorieParNom(categorieNom)
                    ?: Categorie(nom = categorieNom, image = "defaut").also {
                        it.id = db.categorieDao().insert(it)
                    }
                categorie.id
            }

            withContext(Dispatchers.IO) {
                val recette = Recette(
                    nomRecette = nom,
                    image = imageUri.toString(),
                    ingredients = ingredients,
                    categorieId = categorieId
                )
                db.recetteDao().insertRecetteAvecEtapes(recette, etapes)
            }

            _message.postValue("Recette enregistrée avec succès")

            _fermerActivity.postValue(true)
        }
    }
}