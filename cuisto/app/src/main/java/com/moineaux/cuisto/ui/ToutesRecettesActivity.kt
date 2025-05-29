package com.moineaux.cuisto.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.view.KeyEvent
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
import com.moineaux.cuisto.viewmodel.RecetteViewModelFactory
import kotlinx.coroutines.launch

class ToutesRecettesActivity : AppCompatActivity() {

    private lateinit var viewModel: RecetteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toutes_recettes)

        val factory = RecetteViewModelFactory(application)
        viewModel = ViewModelProvider(this, factory)[RecetteViewModel::class.java]

        afficherCategoriesDynamiquement()
        val btnAjouter = findViewById<Button>(R.id.btn_ajouter)
        btnAjouter.setOnClickListener {
            val intent = Intent(this, AjouterOuEditerRecetteActivity::class.java)
            startActivity(intent)
        }

        // Logo pour revenir à l'accueil
        findViewById<ImageView>(R.id.logo_cuisto).setOnClickListener {
            val intent = Intent(this, AccueilActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        viewModel.messageRecetteVide.observe(this) { message ->
            Toast.makeText(this, "Aucun Recette trouvé", Toast.LENGTH_LONG).show()
        }


        var filtre = intent.getStringExtra("itemRechercher")


        // :A valider
        //viewModel.chargerRecettes(filtre)
        val barreRecherche: EditText = findViewById(R.id.champ_recherche)

        barreRecherche.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                val filtre = barreRecherche.text.toString()
                viewModel.chargerRecettes(filtre)
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }



        val layoutPrincipal = findViewById<LinearLayout>(R.id.scroll_layout)

        // ********** Logo pour revenir à l'accueil ************

        findViewById<ImageView>(R.id.logo_cuisto).setOnClickListener {
            val intent = Intent(this, AccueilActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        // *************** Petit délai pour laisser les données s’insérer ************************

        lifecycleScope.launch {
            kotlinx.coroutines.delay(1000)
            viewModel.chargerRecettes(filtre)

            viewModel.recettesParCategorie.collect { recettesParCategorie ->
                layoutPrincipal.removeAllViews()

                recettesParCategorie.forEach { (nomCategorie, recettesDansCategorie) ->
                    val blocCategorie = layoutInflater.inflate(
                        R.layout.bloc_recette_par_categorie,
                        layoutPrincipal,
                        false
                    )
                    val titreCategorie =
                        blocCategorie.findViewById<TextView>(R.id.titre_categorie)
                    val conteneurRecettes =
                        blocCategorie.findViewById<LinearLayout>(R.id.conteneur_recettes)

                    titreCategorie.text = nomCategorie

                    recettesDansCategorie.forEach { recette ->
                        val carte = layoutInflater.inflate(
                            R.layout.article_recette_horizontal,
                            conteneurRecettes,
                            false
                        )
                        val image = carte.findViewById<ImageView>(R.id.image_recette)
                        val nom = carte.findViewById<TextView>(R.id.nom_recette)

                        var imageAffiche = recette.image
                        val resId =resources.getIdentifier(imageAffiche, "drawable", packageName)
                        if(resId!=0) {
                            image.setImageResource(resId)
                        } else {
                            image.setImageURI(imageAffiche.toUri())
                        }
                        carte.setOnClickListener {
                            val intent = Intent(this@ToutesRecettesActivity, AfficherRecetteDetailActivity::class.java)
                            intent.putExtra("recetteid", recette.id)
                            startActivity(intent)
//                            finish()
                        }





                        nom.text = recette.nomRecette

                        conteneurRecettes.addView(carte)
                    }

                    layoutPrincipal.addView(blocCategorie)
                }
            }
        }
    }

    //    ******************** Barre des categorie dynamique ******************

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

                // ****************** Nom de la catégorie *******************

                texte.text = cat.nom

                // Image (depuis drawable)
                val resId = resources.getIdentifier(cat.image, "drawable", packageName)
                if (resId != 0) {
                    Glide.with(this@ToutesRecettesActivity).load(resId).into(image)
                } else {
                    image.setImageResource(R.drawable.defaut)
                }

                // ************* Clique sur la catégorie → ouvre la page des recettes **************

                bloc.setOnClickListener {
                    val intent = Intent(this@ToutesRecettesActivity, RecettesParCategorie::class.java)
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
}

