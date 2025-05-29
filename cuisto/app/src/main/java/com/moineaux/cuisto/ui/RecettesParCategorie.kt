package com.moineaux.cuisto.ui

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.moineaux.cuisto.R

class RecettesParCategorie : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recettes_par_categorie)

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

//        *************** LOGO POUR REVENIR SUR LA PAGE D'ACCUEIL ************

        findViewById<ImageView>(R.id.logo_cuisto).setOnClickListener {
            val intent = Intent(this, AccueilActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        // On recupere le nom de la categorie passe
        // Pas Besoin d'afficher le nom de la categorie
//        val nomCategorie = intent.getStringExtra("categorie") ?: "Inconnue"
//        findViewById<TextView>(R.id.texte_categorie).text = "CATEGORIE / $nomCategorie"
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

        // ****** Affichage du GIF SWIPE
        val gifImageView = findViewById<ImageView>(R.id.gif_cuisto)

        Glide.with(this)
            .asGif()
            .load(R.drawable.swipe)
            .into(gifImageView)

//        ************ Insertion categorie dynamiquement ****************

        val nomCategorie = intent.getStringExtra("categorie") ?: "Inconnue"
        val texteCategorie = findViewById<TextView>(R.id.texte_categorie)
        texteCategorie.text =  "$nomCategorie"


    }
    private fun ouvrirCategorie(nomCategorie: String) {
        val categorieActuelle = intent.getStringExtra("categorie")
        if (categorieActuelle != nomCategorie) {
            val intent = Intent(this, RecettesParCategorie::class.java)
            intent.putExtra("categorie", nomCategorie)
            startActivity(intent)
            finish()
        }
    }


}
