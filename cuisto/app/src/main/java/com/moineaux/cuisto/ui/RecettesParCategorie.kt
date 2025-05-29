package com.moineaux.cuisto.ui

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.moineaux.cuisto.R
import com.moineaux.cuisto.data.AppDatabase
import com.moineaux.cuisto.data.Categorie
import com.moineaux.cuisto.viewmodel.RecetteViewModel
import kotlinx.coroutines.launch

class RecettesParCategorie : AppCompatActivity() {

    private lateinit var viewModel: RecetteViewModel
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recettes_par_categorie)
        afficherCategoriesDynamiquement()

        val barreRecherche: EditText = findViewById(R.id.champ_recherche)
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

        val btnAjouterRecette: Button = findViewById(R.id.btn_ajouter)
        btnAjouterRecette.setOnClickListener {
            val intention = Intent(this, AjouterOuEditerRecetteActivity::class.java)
            startActivity(intention)
        }

//        *************** LOGO POUR REVENIR SUR LA PAGE D'ACCUEIL ************

        findViewById<ImageView>(R.id.logo_cuisto).setOnClickListener {
            val intent = Intent(this, AccueilActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        // On récupère le nom de la catégorie envoyée par l’écran précédent
        val nomCategorie = intent.getStringExtra("categorie") ?: return

        // On affiche ce nom dans le bouton marron au dessus de l'image --- activity_recettes_par_categorie.xml
        findViewById<TextView>(R.id.texte_categorie).text = nomCategorie

        // On initialise la base de données et le ViewModel
        database = AppDatabase.getDatabase(applicationContext)
        viewModel = ViewModelProvider(this)[RecetteViewModel::class.java]

        // On lance une tâche pour récupérer les recettes
        lifecycleScope.launch {
            // On cherche la catégorie dans la base
            val categorie = database.categorieDao().getCategorieParNom(nomCategorie)

            if (categorie != null) {
                val idCategorie = categorie.id

                // On observe les recettes liées à cette catégorie
                viewModel.getRecettesParCategorie(idCategorie).observe(this@RecettesParCategorie) { recettes ->

                    // On récupère la zone où on va afficher les recettes
                    val layoutRecettes = findViewById<LinearLayout>(R.id.zone_dynamique_recettes)

                    // On vide ce qu’il y avait avant (au cas où)
                    layoutRecettes.removeAllViews()

                    // Pour chaque recette, on ajoute un petit bloc
                    for (recette in recettes) {
                        val bloc = layoutInflater.inflate(R.layout.item_recette, layoutRecettes, false)

                        val image = bloc.findViewById<ImageView>(R.id.image_recette)
                        val titre = bloc.findViewById<TextView>(R.id.titre_recette)

                        // On charge l’image (depuis drawable ou depuis un lien http)
                        val resId = resources.getIdentifier(recette.image, "drawable", packageName)
                        if(resId!=0) {
                            image.setImageResource(resId)
                        } else {
                            image.setImageURI(recette.image.toUri())
                        }

                        image.setOnClickListener {
                            val intention = Intent(this@RecettesParCategorie, AfficherRecetteDetailActivity::class.java)
                            intention.putExtra("recetteid", recette.id)
                            startActivity(intention)
                        }

                        // On affiche le nom de la recette
                        titre.text = recette.nomRecette

                        // On ajoute ce bloc dans la page
                        layoutRecettes.addView(bloc)
                    }
                }
            }
        }

    }
    private fun ouvrirCategorie(nomCategorie: String) {
        val intent = Intent(this, RecettesParCategorie::class.java)
        intent.putExtra("categorie", nomCategorie)
        startActivity(intent)
    }
    //    ******************** Barre des categorie dynamique

    private fun afficherCategoriesDynamiquement() {
        val layoutCategories = findViewById<LinearLayout>(R.id.zone_dynamique_categories)
        val db = AppDatabase.getDatabase(applicationContext)

        lifecycleScope.launch {
            val categories: List<Categorie> = db.categorieDao().getToutesLesCategories()

            layoutCategories.removeAllViews()
            val inflater = layoutInflater

            for (cat in categories) {
                val bloc = inflater.inflate(R.layout.item_categorie, layoutCategories, false)

                val image = bloc.findViewById<ImageView>(R.id.image_categorie)
                val texte = bloc.findViewById<TextView>(R.id.texte_categorie)

                // Nom de la catégorie
                texte.text = cat.nom

                // Image (depuis drawable)
                val resId = resources.getIdentifier(cat.image, "drawable", packageName)
                if (resId != 0) {
                    Glide.with(this@RecettesParCategorie).load(resId).into(image)
                } else {
                    image.setImageResource(R.drawable.defaut)
                }

                // *********** Clique sur la catégorie → ouvre la page des recettes *************

                bloc.setOnClickListener {
                    val intent = Intent(this@RecettesParCategorie, RecettesParCategorie::class.java)
                    intent.putExtra("categorie", cat.nom)
                    startActivity(intent)
                }

                layoutCategories.addView(bloc)
            }
        }
    }
}
