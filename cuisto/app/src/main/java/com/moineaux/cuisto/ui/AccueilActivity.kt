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

class AccueilActivity : AppCompatActivity() {

    private val recetteViewModel: RecetteViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accueil)

        // Insertion des données fictives au démarrage via ViewModel
        recetteViewModel.insererDonneesMock()
        recetteViewModel.initialiserMockSiVide()

        val boutonToutesRecettes = findViewById<Button>(R.id.btn_toutes_recettes)
        boutonToutesRecettes.setOnClickListener {
            val intent = Intent(this, ToutesRecettesActivity::class.java)
            startActivity(intent)
        }

        findViewById<ImageView>(R.id.cat_pizza).setOnClickListener { ouvrirCategorie("Pizza") }
        findViewById<ImageView>(R.id.cat_salade).setOnClickListener { ouvrirCategorie("Salade") }
        findViewById<ImageView>(R.id.cat_tacos).setOnClickListener { ouvrirCategorie("Tacos") }
        findViewById<ImageView>(R.id.cat_gateau).setOnClickListener { ouvrirCategorie("Gateau") }
        findViewById<ImageView>(R.id.cat_burger).setOnClickListener { ouvrirCategorie("Burger") }
        findViewById<ImageView>(R.id.cat_pates).setOnClickListener { ouvrirCategorie("Pâtes") }

        afficherRecettesMock()
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
