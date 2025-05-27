package com.moineaux.cuisto.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.moineaux.cuisto.R

class AccueilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accueil)
        val boutonToutesRecettes = findViewById<Button>(R.id.btn_toutes_recettes)
        boutonToutesRecettes.setOnClickListener {
            val intent = Intent(this, ToutesRecettesActivity::class.java)
            startActivity(intent)
        }
    }
}
