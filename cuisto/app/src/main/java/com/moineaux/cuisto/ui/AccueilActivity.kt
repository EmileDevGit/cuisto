package com.moineaux.cuisto.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.moineaux.cuisto.R
import com.moineaux.cuisto.data.AppDatabase
import com.moineaux.cuisto.viewmodel.RecetteViewModel
import kotlinx.coroutines.launch
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.moineaux.cuisto.data.Categorie

class AccueilActivity : AppCompatActivity() {

    private val recetteViewModel: RecetteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accueil)

        val boutonToutesRecettes = findViewById<Button>(R.id.btn_toutes_recettes)
        boutonToutesRecettes.setOnClickListener {
            val intent = Intent(this, ToutesRecettesActivity::class.java)
            startActivity(intent)
        }

        // *********** On initialise les données mock si besoin PUIS on affiche ************

        recetteViewModel.initialiserMockSiVide {
            afficherRecettesMock()
            afficherCategoriesDynamiquement()
        }
    }

//    ******************** Barre des categorie dynamique **************************

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

                // ************* Nom de la catégorie ******************

                texte.text = cat.nom

                // ************** Image (depuis drawable) **********************

                val resId = resources.getIdentifier(cat.image, "drawable", packageName)
                if (resId != 0) {
                    Glide.with(this@AccueilActivity).load(resId).into(image)
                } else {
                    image.setImageResource(R.drawable.defaut)
                }

                // ************** Clique sur la catégorie → ouvre la page des recettes ****************

                bloc.setOnClickListener {
                    val intent = Intent(this@AccueilActivity, RecettesParCategorie::class.java)
                    intent.putExtra("categorie", cat.nom)
                    startActivity(intent)
                }

                layoutCategories.addView(bloc)
            }
        }
    }

    private fun ouvrirCategorie(nomCategorie: String) {
        val intent = Intent(this, RecettesParCategorie::class.java)
        intent.putExtra("categorie", nomCategorie)
        startActivity(intent)
    }

    private fun afficherRecettesMock() {
        val grilleRecettes = findViewById<GridLayout>(R.id.grille_recettes)

        recetteViewModel.chargerRecettesMock { recettes ->
            runOnUiThread {
                grilleRecettes.removeAllViews()
                for (recette in recettes) {
                    val frame = layoutInflater.inflate(R.layout.article_recette_carte, grilleRecettes, false)
                    val imageView = frame.findViewById<ImageView>(R.id.image_recette)
                    val texteView = frame.findViewById<TextView>(R.id.nom_recette)

                    val resId = resources.getIdentifier(recette.image, "drawable", packageName)
                    if (resId != 0) {
                        imageView.setImageResource(resId)
                    } else {
                        imageView.setImageResource(R.drawable.defaut)
                    }

                    texteView.text = recette.nomRecette
                    grilleRecettes.addView(frame)
                }
            }
        }
    }

}
