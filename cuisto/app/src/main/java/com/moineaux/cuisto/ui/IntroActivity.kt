package com.moineaux.cuisto.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.moineaux.cuisto.R
import androidx.core.content.edit

class IntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("prefs", MODE_PRIVATE)

        // Vérifie si c'est la première ouverture
        val isFirstLaunch = prefs.getBoolean("is_first_launch", true)
        if (!isFirstLaunch) {
            // L'utilisateur a déjà ouvert l'app : aller directement à l'accueil
            startActivity(Intent(this, AccueilActivity::class.java))
            finish()
            return
        }

        // Sinon, on affiche l'écran d'intro
        setContentView(R.layout.activity_intro)

        val boutonDecouvrir = findViewById<Button>(R.id.bouton_decouvrir)
        boutonDecouvrir.setOnClickListener {
            // Enregistre que l'utilisateur a déjà ouvert l'app
            prefs.edit() { putBoolean("is_first_launch", false) }

            // Redirige vers l'accueil
            startActivity(Intent(this, AccueilActivity::class.java))
            finish()
        }
    }
}
