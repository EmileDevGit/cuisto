package com.moineaux.cuisto.ui

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.moineaux.cuisto.R
import com.moineaux.cuisto.viewmodel.AfficherRecetteDetailViewModel
import com.bumptech.glide.Glide

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
        barreRecherche.setOnEditorActionListener { v, idAction, evenement ->
            // Verifier si la touche entree est tape ou meme pour le clavier virtuel
            if (idAction == EditorInfo.IME_ACTION_SEARCH ||
                idAction == EditorInfo.IME_ACTION_DONE ||
                (evenement != null && evenement.keyCode == KeyEvent.KEYCODE_ENTER && evenement.action == KeyEvent.ACTION_DOWN)) {

                val itemRechercher = barreRecherche.text.toString()
                val intention = Intent(this, ToutesRecettesActivity::class.java)
                intention.putExtra("itemRechercher", itemRechercher)
                startActivity(intention)

                // Cacher clavier virtuel
                val imm = getSystemService(INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)

                return@setOnEditorActionListener true // Consommer l evenement
            }
            return@setOnEditorActionListener false // laisser le system avec les autres actions
        }

        rvEtapes.adapter = adapterEtape

        ivLogo.setOnClickListener {
            val intention = Intent(this, AccueilActivity::class.java)
            startActivity(intention)
            finish()
        }
        btnAjouterRecette.setOnClickListener {
            val intention = Intent(this, AjouterRecetteActivity::class.java)
            startActivity(intention)
            finish()
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
                    Glide.with(this)
                        .load(idResource)
                        .placeholder(R.drawable.imgdefaut_recette)
                        .error(R.drawable.imgdefaut_recette)
                        .into(ivRecette)
                } else {
                    // Si c'est un autre chemin (fichier de l utilisateur)
                    Glide.with(this)
                        .load(srcImage) // charger a partir d un chemin
                        .placeholder(R.drawable.imgdefaut_recette)
                        .error(R.drawable.imgdefaut_recette)
                        .into(ivRecette)
                }
            } else {
                ivRecette.setImageResource(R.drawable.imgdefaut_recette)
            }
        }

        val idRecette = intent.getLongExtra("recetteid", 0)

        recetteDetailViewModel.afficherRecette( idRecette )
    }
}