package com.moineaux.cuisto.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Button
import android.widget.EditText
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
import android.view.inputmethod.EditorInfo
import android.widget.Toast

class AccueilActivity : AppCompatActivity() {

    private val recetteViewModel: RecetteViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accueil)

        // Insertion des données fictives au démarrage via ViewModel
        recetteViewModel.insererDonneesMock()
        recetteViewModel.initialiserMockSiVide()
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
        // *********************************** Vide la base avant tout  ------------ ATTENTION A RETIRER POUR LA PRODUCTION ****************************
//        Thread {
//            db.recetteDao().supprimerToutesLesRecettes()
//            db.categorieDao().supprimerToutesLesCategories()
//
//            // ******************************** Insérer les données mock ******************************************
//            MockData.insererDonneesMock(db)
//        }.start()

        // ************ Ecouteur bouton recette *********************





        val boutonToutesRecettes = findViewById<Button>(R.id.btn_toutes_recettes)
        boutonToutesRecettes.setOnClickListener {
            val intent = Intent(this, ToutesRecettesActivity::class.java)
            intent.putExtra("itemRechercher", "")
            startActivity(intent)
        }


                                // Logique à réutiliser pour la modification
        val btnAjouterRecette = findViewById<Button>(R.id.btnNouvelleRecette)
        btnAjouterRecette.setOnClickListener {
            val intent = Intent(this, AjouterRecetteActivity::class.java)
            intent.putExtra("is_new_recette", true)
            intent.putExtra("recette_id", -1)
            startActivity(intent)
        }

        // ************ Ecouteur sur images categorie *********************

        // CLIC SUR CATEGORIE PIZZA
        findViewById<ImageView>(R.id.cat_pizza).setOnClickListener {
            ouvrirCategorie("Pizza")
        }

        findViewById<ImageView>(R.id.cat_pizza).setOnClickListener { ouvrirCategorie("Pizza") }
        findViewById<ImageView>(R.id.cat_salade).setOnClickListener { ouvrirCategorie("Salade") }
        findViewById<ImageView>(R.id.cat_tacos).setOnClickListener { ouvrirCategorie("Tacos") }
        findViewById<ImageView>(R.id.cat_gateau).setOnClickListener { ouvrirCategorie("Gateau") }
        findViewById<ImageView>(R.id.cat_burger).setOnClickListener { ouvrirCategorie("Burger") }
        findViewById<ImageView>(R.id.cat_pates).setOnClickListener { ouvrirCategorie("Pâtes") }

        afficherRecettesMock()
        // afficherRecettes(db)
    }

    private fun ouvrirCategorie(nomCategorie: String) {
        val intent = Intent(this, RecettesParCategorie::class.java)
        intent.putExtra("categorie", nomCategorie)
        startActivity(intent)
    }

    private fun afficherRecettes(db: AppDatabase, filtre: String = "") {
        val grilleRecettes = findViewById<GridLayout>(R.id.grille_recettes)

        // *************** Lance ce code de manière asynchrone, mais arrête-le automatiquement si l'activité est détruite (ex: quand l'utilisateur quitte l'écran) ***************
        lifecycleScope.launch {



            var recettes = db.recetteDao().recupererRecettesMock()


            // ****************** POUR DEBUGGER + DANS LOGCAT : recherche DEBUG_RECETTES **************************

            Log.d("DEBUG_RECETTES", "Recettes récupérées : $recettes")
            grilleRecettes.removeAllViews()
            recettes.forEach { recette ->

//          *************** Creation de carte dynamiquement avec en model article_recette_carte *************

                val frame =
                    layoutInflater.inflate(R.layout.article_recette_carte, grilleRecettes, false)


                val imageView = frame.findViewById<ImageView>(R.id.image_recette)
                val texteView = frame.findViewById<TextView>(R.id.nom_recette)

                val resId = resources.getIdentifier(recette.image, "drawable", packageName)

                if (resId != 0) {
                    imageView.setImageResource(resId)
                } else {
                    imageView.setImageResource(R.drawable.imgdefaut_recette) // imageDefaut
                    println("Image introuvable pour : ${recette.image}")
                }

                frame.setOnClickListener {

                    val intention =
                        Intent(this@AccueilActivity, AfficherRecetteDetailActivity::class.java)
                    intention.putExtra("recetteid", recette.id)
                    startActivity(intention)

                }

                texteView.text = recette.nomRecette
                grilleRecettes.addView(frame)
            }
        }
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
                    frame.setOnClickListener {

                        val intention =
                            Intent(this@AccueilActivity, AfficherRecetteDetailActivity::class.java)
                        intention.putExtra("recetteid", recette.id)
                        startActivity(intention)

                    }

                    texteView.text = recette.nomRecette
                    grilleRecettes.addView(frame)
                }
            }
        }
    }

}
