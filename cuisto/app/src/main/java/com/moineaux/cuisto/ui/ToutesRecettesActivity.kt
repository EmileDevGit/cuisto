package com.moineaux.cuisto.ui

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.widget.Button
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.moineaux.cuisto.R
import com.moineaux.cuisto.data.AppDatabase
import com.moineaux.cuisto.repository.RecetteRepository
import com.moineaux.cuisto.viewmodel.RecetteViewModel
import com.moineaux.cuisto.viewmodel.RecetteViewModelFactory
import com.moineaux.cuisto.data.MockData
import com.moineaux.cuisto.data.Recette
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ToutesRecettesActivity : AppCompatActivity() {

    private lateinit var viewModel: RecetteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toutes_recettes)

        val btnAjouter = findViewById<Button>(R.id.btn_ajouter)
        btnAjouter.setOnClickListener {
            val intent = Intent(this, AjouterRecetteActivity::class.java)
            startActivity(intent)
        }

        // Logo pour revenir à l'accueil
        findViewById<ImageView>(R.id.logo_cuisto).setOnClickListener {
            val intent = Intent(this, AccueilActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }


        var filtre = intent.getStringExtra("itemRechercher")

        // ViewModel + Factory
        val factory = RecetteViewModelFactory(application)
        viewModel = ViewModelProvider(this, factory)[RecetteViewModel::class.java]
        viewModel.chargerRecettes(filtre)
        val barreRecherche: EditText = findViewById(R.id.champ_recherche)

        barreRecherche.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                val itemRechercher = barreRecherche.text.toString()
                viewModel.chargerRecettes(itemRechercher)
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }



        val layoutPrincipal = findViewById<LinearLayout>(R.id.scroll_layout)

        // Observer et afficher les recettes groupées par catégorie
        lifecycleScope.launch {
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
}

