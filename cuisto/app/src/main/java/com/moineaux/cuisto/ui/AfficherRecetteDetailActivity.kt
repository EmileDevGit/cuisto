package com.moineaux.cuisto.ui

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.moineaux.cuisto.R
import com.moineaux.cuisto.viewmodel.AfficherRecetteDetailViewModel

class AfficherRecetteDetailActivity : AppCompatActivity() {

    private val recetteDetailViewModel: AfficherRecetteDetailViewModel by viewModels()
    private lateinit var adapterEtape: AfficherRecetteEtapesAdapter
    private lateinit var rvEtapes: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_afficher_recette_detail)


        val idRecette = intent.getLongExtra("recetteid", 0)
        recetteDetailViewModel.afficherRecette( idRecette )


        val barreRecherche: EditText = findViewById(R.id.etRechercheRecetteDetail)
        barreRecherche.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {

                val itemRechercher = barreRecherche.text.toString()
                val intention = Intent(this, ToutesRecettesActivity::class.java)
                intention.putExtra("itemRechercher", itemRechercher)
                startActivity(intention)
                finish()
                return@setOnKeyListener true

            }
            return@setOnKeyListener false
        }



        val ivLogo: ImageView = findViewById(R.id.imgVLogoRecetteDetail)
        ivLogo.setOnClickListener {
            finish()
        }



        val btnAjouterRecette: Button = findViewById(R.id.btnAjouteRecetteDetail)
        btnAjouterRecette.setOnClickListener {
            val intention = Intent(this, AjouterOuEditerRecetteActivity::class.java)
            startActivity(intention)
        }



        val btnModifierRecette: Button = findViewById(R.id.btnEditerCategorie)
        btnModifierRecette.setOnClickListener {
            val intention = Intent(this, AjouterOuEditerRecetteActivity::class.java)
            intention.putExtra("is_new_recette", false)
            intention.putExtra("recette_id", idRecette)
            startActivity(intention)
        }

        val btnSupprimerRecette: Button = findViewById(R.id.btnSupprimerCategorie)
        btnSupprimerRecette.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.titre_supprimerRecette))
                .setMessage(getString(R.string.confirmation_textSupprimer))
                .setPositiveButton(getString(R.string.text_oui)) { _, _ ->
                    recetteDetailViewModel.supprimerRecette(idRecette)
                    Toast.makeText(this,
                        getString(R.string.msg_recetteDetail_supprimer), Toast.LENGTH_SHORT).show()
                    finish() // Fermer l'activité après la suppression
                    val intention = Intent(this, ToutesRecettesActivity::class.java)
                    startActivity(intention)
                }
                .setNegativeButton(getString(R.string.bouton_annuler_AjouterRecette), null)
                .show()
        }
        recetteDetailViewModel.isMock.observe(this) { mockValue ->
            btnSupprimerRecette.visibility = if (mockValue == 0) Button.VISIBLE else Button.GONE
            btnModifierRecette.visibility = btnSupprimerRecette.visibility
        }



        val tvIngredients: TextView = findViewById(R.id.tvIngredientsActuelRecetteDetail)
        recetteDetailViewModel.ingredientsRecetteDetail.observe(this) { ingredients ->
            tvIngredients.text = ingredients
        }



        adapterEtape = AfficherRecetteEtapesAdapter()
        rvEtapes = findViewById(R.id.rvEtapesRecetteDetail)
        rvEtapes.adapter = adapterEtape
        recetteDetailViewModel.listEtapeRecetteDetail.observe(this) { etapes ->
            adapterEtape.submitList(etapes)
        }



        val tvNom: TextView = findViewById(R.id.tvNomRecetteDetail)
        recetteDetailViewModel.nomRecetteDetail.observe(this) { nom ->
            tvNom.text = nom
        }



        val tvCategorie: TextView = findViewById(R.id.tvNomCategorieRecetteDetail)
        recetteDetailViewModel.categorieRecetteDetail.observe(this) { categorie ->
            tvCategorie.text = categorie
        }



        val ivRecette: ImageView = findViewById(R.id.imgVPhotoRecetteDetail)
        recetteDetailViewModel.imageUri.observe(this) { srcImage ->
            if (srcImage != null) {
                // Premierement essaie de voir si c'est un drawable (Image avec l'app)
                val idResource = resources.getIdentifier(srcImage, "drawable", packageName)
                if (idResource != 0) {
                    ivRecette.setImageResource(idResource)
                } else {
                    ivRecette.setImageURI(srcImage.toUri())
                }
            } else {
                ivRecette.setImageResource(R.drawable.imgdefaut_recette)
            }
        }




    }
}