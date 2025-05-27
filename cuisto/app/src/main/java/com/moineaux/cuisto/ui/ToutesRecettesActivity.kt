package com.moineaux.cuisto.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.moineaux.cuisto.R

class ToutesRecettesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toutes_recettes)

        // ****************** PERMET DAVOIR DEUX COULEUR SUR LE TEXTE ***************

        val texteCategorie = findViewById<TextView>(R.id.texte_categorie)

        val prefixe = "CATEGORIE / "
        val nomCategorie = "Pizza" // A recuperer pour le cote dynamique
        val texteComplet = prefixe + nomCategorie

        val spannable = SpannableString(texteComplet)
        spannable.setSpan(StyleSpan(Typeface.BOLD), 0, prefixe.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(ForegroundColorSpan(Color.parseColor("#FF9800")), prefixe.length, texteComplet.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        texteCategorie.text = spannable

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