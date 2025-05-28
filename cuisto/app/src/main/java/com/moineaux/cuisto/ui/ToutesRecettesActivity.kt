package com.moineaux.cuisto.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.moineaux.cuisto.R
import com.moineaux.cuisto.data.AppDatabase
import com.moineaux.cuisto.repository.RecetteRepository
import com.moineaux.cuisto.viewmodel.RecetteViewModel
import com.moineaux.cuisto.viewmodel.RecetteViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ToutesRecettesActivity : AppCompatActivity() {

    private lateinit var viewModel: RecetteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toutes_recettes)

        // Initialisation base de données et repository
        val db = AppDatabase.getDatabase(this)
        val repository = RecetteRepository(db)

        // ViewModel + Factory
        val factory = RecetteViewModelFactory(application)
        viewModel = ViewModelProvider(this, factory)[RecetteViewModel::class.java]
        viewModel.chargerRecettes()

        val layoutPrincipal = findViewById<LinearLayout>(R.id.scroll_layout)

        // Observer et afficher les recettes groupées par catégorie
        lifecycleScope.launch {
            viewModel.recettesParCategorie.collect { recettesParCategorie ->
                layoutPrincipal.removeAllViews()

                recettesParCategorie.forEach { (nomCategorie, recettesDansCategorie) ->
                    val blocCategorie = layoutInflater.inflate(R.layout.bloc_recette_par_categorie, layoutPrincipal, false)
                    val titreCategorie = blocCategorie.findViewById<TextView>(R.id.titre_categorie)
                    val conteneurRecettes = blocCategorie.findViewById<LinearLayout>(R.id.conteneur_recettes)

                    titreCategorie.text = nomCategorie

                    recettesDansCategorie.forEach { recette ->
                        val carte = layoutInflater.inflate(R.layout.article_recette_horizontal, conteneurRecettes, false)
                        val image = carte.findViewById<ImageView>(R.id.image_recette)
                        val nom = carte.findViewById<TextView>(R.id.nom_recette)

                        val resId = resources.getIdentifier(recette.image, "drawable", packageName)
                        image.setImageResource(if (resId != 0) resId else R.drawable.defaut)
                        nom.text = recette.nomRecette

                        conteneurRecettes.addView(carte)
                    }

                    layoutPrincipal.addView(blocCategorie)
                }
            }
        }

        // Logo pour revenir à l'accueil
        findViewById<ImageView>(R.id.logo_cuisto).setOnClickListener {
            val intent = Intent(this, AccueilActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
    }
}
