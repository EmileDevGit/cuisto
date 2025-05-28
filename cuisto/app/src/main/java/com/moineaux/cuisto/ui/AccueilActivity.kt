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
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.moineaux.cuisto.R
import com.moineaux.cuisto.data.AppDatabase
import com.moineaux.cuisto.data.MockData
import kotlinx.coroutines.launch
import android.view.inputmethod.EditorInfo

class AccueilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accueil)

        val barreRecherche: EditText = findViewById(R.id.champ_recherche)
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

        // Mock
        val db = AppDatabase.getDatabase(this)
        // *********************************** Vide la base avant tout  ------------ ATTENTION A RETIRER POUR LA PRODUCTION ****************************
//        Thread {
//            db.recetteDao().supprimerToutesLesRecettes()
//            db.categorieDao().supprimerToutesLesCategories()
//
//            // ******************************** Insérer les données mock ******************************************
//            MockData.insererDonneesMock(db)
//        }.start()
        MockData.insererDonneesMock(db)

        // ************ Ecouteur bouton recette *********************

        val boutonToutesRecettes = findViewById<Button>(R.id.btn_toutes_recettes)
        boutonToutesRecettes.setOnClickListener {
            val intent = Intent(this, ToutesRecettesActivity::class.java)
            startActivity(intent)
        }
        // ************ Ecouteur sur images categorie *********************

        // CLIC SUR CATEGORIE PIZZA
        findViewById<ImageView>(R.id.cat_pizza).setOnClickListener {
            ouvrirCategorie("Pizza")
        }

        // CLIC SUR CATEGORIE SALADE
        findViewById<ImageView>(R.id.cat_salade).setOnClickListener {
            ouvrirCategorie("Salade")
        }

        // CLIC SUR CATEGORIE TACOS
        findViewById<ImageView>(R.id.cat_tacos).setOnClickListener {
            ouvrirCategorie("Tacos")
        }

        // CLIC SUR CATEGORIE GATEAU
        findViewById<ImageView>(R.id.cat_gateau).setOnClickListener {
            ouvrirCategorie("Gateau")
        }
        // CLIC SUR CATEGORIE BURGER
        findViewById<ImageView>(R.id.cat_burger).setOnClickListener {
            ouvrirCategorie("Burger")
        }
        // CLIC SUR CATEGORIE PATES
        findViewById<ImageView>(R.id.cat_pates).setOnClickListener {
            ouvrirCategorie("Pates")
        }


        afficherRecettes(db)

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
            for (recette in recettes) {

//          *************** Creation de carte dynamiquement avec en model article_recette_carte *************

                val frame = layoutInflater.inflate(R.layout.article_recette_carte, grilleRecettes, false)


                val imageView = frame.findViewById<ImageView>(R.id.image_recette)
                val texteView = frame.findViewById<TextView>(R.id.nom_recette)

                val resId = resources.getIdentifier(recette.image, "drawable", packageName)

                if (resId != 0) {
                    imageView.setImageResource(resId)
                } else {
                    imageView.setImageResource(R.drawable.defaut) // imageDefaut
                    println("Image introuvable pour : ${recette.image}")
                }
                texteView.text = recette.nomRecette

                grilleRecettes.addView(frame)

                imageView.setOnClickListener {
                    val intention = Intent(this@AccueilActivity, AfficherRecetteDetailActivity::class.java)
                    intention.putExtra("recetteid", recette.id)
                    startActivity(intention)
                }
            }
        }
    }

}
