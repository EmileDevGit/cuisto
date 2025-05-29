package com.moineaux.cuisto.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.moineaux.cuisto.R

class IntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        val boutonDecouvrir = findViewById<Button>(R.id.bouton_decouvrir)
        boutonDecouvrir.setOnClickListener {

            val intention = Intent(this, AccueilActivity::class.java)
            startActivity(intention)
            finish()
        }

    }
}
