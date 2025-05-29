package com.moineaux.cuisto.ui

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.moineaux.cuisto.R
import com.moineaux.cuisto.viewmodel.AfficherRecetteDetailViewModel

class AfficherRecetteDetailActivity : AppCompatActivity() {

    private val recetteDetailViewModel: AfficherRecetteDetailViewModel by viewModels()
    private lateinit var adapterEtape: AfficherRecetteEtapesAdapter
    private lateinit var rvEtapes: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_afficher_recette_detail)

        adapterEtape = AfficherRecetteEtapesAdapter()


        rvEtapes = findViewById(R.id.rvEtapesRecetteDetail)
        val tvIngredients: TextView = findViewById(R.id.tvIngredientsActuelRecetteDetail)
        val tvNom: TextView = findViewById(R.id.tvNomRecetteDetail)
        val tvCategorie: TextView = findViewById(R.id.tvNomCategorieRecetteDetail)
        val ivRecette: ImageView = findViewById(R.id.imgVPhotoRecetteDetail)
        val ivLogo: ImageView = findViewById(R.id.imgVLogoRecetteDetail)
        val btnAjouterRecette: Button = findViewById(R.id.btnAjouteRecetteDetail)
        val barreRecherche: EditText = findViewById(R.id.etRechercheRecetteDetail)
        barreRecherche.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                val itemRechercher = barreRecherche.text.toString()
                val intention = Intent(this, ToutesRecettesActivity::class.java)
                intention.putExtra("itemRechercher", itemRechercher)
                startActivity(intention)
                finish()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

        rvEtapes.adapter = adapterEtape

        ivLogo.setOnClickListener {
            val intention = Intent(this, AccueilActivity::class.java)
//            startActivity(intention)
            finish()
        }
        btnAjouterRecette.setOnClickListener {
            val intention = Intent(this, AjouterRecetteActivity::class.java)
            startActivity(intention)
//            finish()
        }

        recetteDetailViewModel.ingredientsRecetteDetail.observe(this) { ingredients ->
            tvIngredients.text = ingredients
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
        recetteDetailViewModel.imageUri.observe(this) { srcImage ->

            if (srcImage != null) {
                // Premierement essaie de voir si c'est un drawable (Image avec l'app)
                val idResource = resources.getIdentifier(srcImage, "drawable", packageName)

                if (idResource != 0) {
                    // C'est un resource 'drawable'
//                    Glide.with(this)
//                        .load(idResource)
//                        .placeholder(R.drawable.imgdefaut_recette)
//                        .error(R.drawable.imgdefaut_recette)
//                        .into(ivRecette)
                    ivRecette.setImageResource(idResource)
                } else {
                    // Si c'est un autre chemin (fichier de l utilisateur)
//                    Glide.with(this)
//                        .load(srcImage.toUri()) // charger a partir d un chemin
//                        .placeholder(R.drawable.imgdefaut_recette)
//                        .error(R.drawable.imgdefaut_recette)
//                        .into(ivRecette)
                    ivRecette.setImageURI(srcImage.toUri())
                }
            } else {
                ivRecette.setImageResource(R.drawable.imgdefaut_recette)
            }
        }

        val idRecette = intent.getLongExtra("recetteid", 0)

        recetteDetailViewModel.afficherRecette( idRecette )
    }
}