package com.moineaux.cuisto.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.moineaux.cuisto.R

class RecettesParCategorie : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recettes_par_categorie)

        // On recupere le nom de la categorie passe
        val nomCategorie = intent.getStringExtra("categorie") ?: "Inconnue"
        findViewById<TextView>(R.id.texte_categorie).text = "CATEGORIE / $nomCategorie"
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
    }
    private fun ouvrirCategorie(nomCategorie: String) {
        val intent = Intent(this, RecettesParCategorie::class.java)
        intent.putExtra("categorie", nomCategorie)
        startActivity(intent)
    }


}
