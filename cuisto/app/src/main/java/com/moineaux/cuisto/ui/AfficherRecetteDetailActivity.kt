package com.moineaux.cuisto.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.moineaux.cuisto.R
import com.moineaux.cuisto.viewmodel.AfficherRecetteDetailViewModel
import androidx.core.net.toUri

class AfficherRecetteDetailActivity : AppCompatActivity() {

    private val recetteDetailViewModel: AfficherRecetteDetailViewModel by viewModels()
    private lateinit var adapterIngredients: AfficherRecetteIngredientsAdapter
    private lateinit var adapterEtape: AfficherRecetteEtapesAdapter
    private lateinit var rvIngredients: RecyclerView
    private lateinit var rvEtapes: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_afficher_recette_detail)

        adapterIngredients = AfficherRecetteIngredientsAdapter()
        adapterEtape = AfficherRecetteEtapesAdapter()

        rvIngredients = findViewById(R.id.rvIngredientsRecetteDetail)
        rvEtapes = findViewById(R.id.rvEtapesRecetteDetail)
        val tvNom: TextView = findViewById(R.id.tvNomRecetteDetail)
        val tvCategorie: TextView = findViewById(R.id.tvNomCategorieRecetteDetail)
        val ivRecette: ImageView = findViewById(R.id.imgVPhotoRecetteDetail)

        rvIngredients.adapter = adapterIngredients
        rvEtapes.adapter = adapterEtape


        recetteDetailViewModel.listIngredientRecetteDetail.observe(this) { ingredients ->
            adapterIngredients.submitList(ingredients)
        }
        recetteDetailViewModel.listEtapeRecetteDetail.observe(this) { etapes ->
            adapterEtape.submitList(etapes)
        }
        recetteDetailViewModel.nomRecetteDetail.observe(this) { nom ->
            tvNom.text = nom
        }
        recetteDetailViewModel.categorieRecetteDetail.observe(this) { categorie ->
            tvCategorie.text = categorie
        }

        recetteDetailViewModel.imageUri.observe(this) { uriString ->
            if (!uriString.isNullOrEmpty()) {
                val uri = uriString.toUri()
                ivRecette.setImageURI(uri)
            } else {
                ivRecette.setImageResource(R.drawable.imgdefaut_recette)
            }
        }

        // Remplacer id du recette avec sur quelle image t appuie sur l accueil
        // Recup a partir de l autre ui
        recetteDetailViewModel.afficherRecette(-1)

    }
}