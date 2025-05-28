package com.moineaux.cuisto.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.moineaux.cuisto.R
import com.moineaux.cuisto.data.AppDatabase
import com.moineaux.cuisto.data.MockData
import com.moineaux.cuisto.data.Recette
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ToutesRecettesActivity : AppCompatActivity() {
    var filtre: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toutes_recettes)
        // Mock
        val db = AppDatabase.getDatabase(this)
        // *********************************** Vide la base avant tout  ------------ ATTENTION A RETIRER POUR LA PRODUCTION ****************************
//        Thread {
//            db.recetteDao().supprimerToutesLesRecettes()
//            db.categorieDao().supprimerToutesLesCategories()
//
//            // ******************************** Insérer les données mock ******************************************
//            MockData.insererDonneesMock(db)
//        }.start()


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

        // Gérez la requête de recherche reçue de l'AccueilActivity ou autre
        val elementRechercheDepuisIntent = intent.getStringExtra("itemRechercher")
        if (!elementRechercheDepuisIntent.isNullOrBlank()) {
            filtre = elementRechercheDepuisIntent
        }


        val layoutPrincipal = findViewById<LinearLayout>(R.id.scroll_layout)

        // *************************  Récupération des recettes **************************

        lifecycleScope.launch {
            var recettes = withContext(Dispatchers.IO) {
                db.recetteDao().toutRecuperer()
            }

            if (filtre.isNotEmpty()) {
                recettes = recettes.filter { it.nomRecette.contains(filtre, ignoreCase = true) }
            } else if (intent.getStringExtra("itemRechercher").isNullOrEmpty()) {
                val filtreAvecIntent = intent.getStringExtra("itemRechercher")
                recettes = recettes.filter { it.nomRecette.contains(filtreAvecIntent.toString(), ignoreCase = true) }
            }

            if (recettes.isEmpty()) {
                Toast.makeText(this@ToutesRecettesActivity, "Aucun recette trouvé", Toast.LENGTH_LONG).show()
            }


            //  ***************** Regroupe les recettes selon leur categorieId ************
            val recettesParCategorie = recettes.groupBy { it.categorieId }

//          Pour chaque catégorie, prends son numéro (categorieId) et la liste des recettes qui vont avec (recettesDansCategorie)
            recettesParCategorie.forEach { (categorieId, recettesDansCategorie) ->

                val blocCategorie = layoutInflater.inflate(R.layout.bloc_recette_par_categorie, layoutPrincipal, false)

                val titreCategorie = blocCategorie.findViewById<TextView>(R.id.titre_categorie)
                val conteneurRecettes = blocCategorie.findViewById<LinearLayout>(R.id.conteneur_recettes)

                val nomCategorie = withContext(Dispatchers.IO) {
                    getNomCategorieDepuisDB(db, categorieId)
                }
                titreCategorie.text = "$nomCategorie"


                recettesDansCategorie.forEach { recette ->
                    val carte = layoutInflater.inflate(R.layout.article_recette_horizontal, conteneurRecettes, false)


                    val image = carte.findViewById<ImageView>(R.id.image_recette)
                    val nom = carte.findViewById<TextView>(R.id.nom_recette)

                    image.setOnClickListener {
                        val intention = Intent(this@ToutesRecettesActivity, AfficherRecetteDetailActivity::class.java)
                        intention.putExtra("recetteid", recette.id)
                        startActivity(intention)
                    }

                    val resId = resources.getIdentifier(recette.image, "drawable", packageName)
                    Log.d("IMAGE_TEST", "Nom image : ${recette.image}, resId = $resId")
                    image.setImageResource(if (resId != 0) resId else R.drawable.defaut)
                    nom.text = recette.nomRecette

                    conteneurRecettes.addView(carte)
                }

                layoutPrincipal.addView(blocCategorie)
            }
        }

        //        *************** LOGO POUR REVENIR SUR LA PAGE D'ACCUEIL ************

        findViewById<ImageView>(R.id.logo_cuisto).setOnClickListener {
            val intent = Intent(this, AccueilActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        // ****************** PERMET DAVOIR DEUX COULEUR SUR LE TEXTE ***************
//
//        val texteCategorie = findViewById<TextView>(R.id.texte_categorie)
//
//        val prefixe = "CATEGORIE / "
//        val nomCategorie = "Pizza" // A recuperer pour le cote dynamique
//        val texteComplet = prefixe + nomCategorie
//
//        val spannable = SpannableString(texteComplet)
//        spannable.setSpan(StyleSpan(Typeface.BOLD), 0, prefixe.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//        spannable.setSpan(ForegroundColorSpan(Color.parseColor("#FF9800")), prefixe.length, texteComplet.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//
//        texteCategorie.text = spannable

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

    // PLUS BESOIN DE CA CAR CA VA DIRECTEMENT DANS LA BASE DE DONNEE

//    fun getNomCategorie(id: Long): String {
//        return when (id) {
//            1L -> "Pizza"
//            2L -> "Salade"
//            3L -> "Burger"
//            4L -> "Pâtes"
//            5L -> "Tacos"
//            else -> "Autre"
//        }
//    }
    private fun ouvrirCategorie(nomCategorie: String) {
        val intent = Intent(this, RecettesParCategorie::class.java)
        intent.putExtra("categorie", nomCategorie)
        startActivity(intent)
    }

    // Pour eviter d'avoir autre ca va chercher le nom dans la base
    suspend fun getNomCategorieDepuisDB(db: AppDatabase, id: Long): String {
        return db.categorieDao().getNomCategorieParId(id) ?: "Autre"
    }

    private fun afficherRecettes(db: AppDatabase) {
        val grilleRecettes = findViewById<GridLayout>(R.id.grille_recettes) // a changer par le container

        // *************** Lance ce code de manière asynchrone, mais arrête-le automatiquement si l'activité est détruite (ex: quand l'utilisateur quitte l'écran) ***************
        lifecycleScope.launch {


            val recettes = db.recetteDao().toutRecuperer()

            // ****************** POUR DEBUGGER + DANS LOGCAT : recherche DEBUG_RECETTES **************************

            Log.d("DEBUG_RECETTES 2", "Recettes récupérées : $recettes")
            grilleRecettes.removeAllViews()
            for (recette in recettes) {

//          *************** Creation de carte dynamiquement avec en model article_recette_carte *************

                val frame = layoutInflater.inflate(R.layout.article_recette_carte, grilleRecettes, false)


                val imageView = frame.findViewById<ImageView>(R.id.image_recette)
                val texteView = frame.findViewById<TextView>(R.id.nom_recette)

                val resId = resources.getIdentifier(recette.image, "drawable", packageName)

                if (resId != 0) {
                    imageView.setImageResource(resId)
                } else {
                    imageView.setImageResource(R.drawable.defaut) // imageDefaut
                    println("Image introuvable pour : ${recette.image}")
                }
                texteView.text = recette.nomRecette

                grilleRecettes.addView(frame)
            }
        }
    }
}