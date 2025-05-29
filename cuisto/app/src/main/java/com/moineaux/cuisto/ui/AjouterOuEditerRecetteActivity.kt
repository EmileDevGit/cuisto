package com.moineaux.cuisto.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moineaux.cuisto.R
import android.net.Uri
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.moineaux.cuisto.data.Etape
import androidx.core.net.toUri

class AjouterOuEditerRecetteActivity() : AppCompatActivity() {

    // Variables à initialiser plus tard
    private lateinit var etNomRecette: EditText
    private lateinit var etIngredients: EditText
    private lateinit var actvCategorie: AutoCompleteTextView

    private val ajouterOuEditerRecetteViewModel: AjouterOuEditerRecetteViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EtapeAdapter
    private lateinit var itemTouchHelper: ItemTouchHelper
    private lateinit var btnChoisirImage: ImageButton


    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            btnChoisirImage.setImageURI(uri)
            ajouterOuEditerRecetteViewModel.definirImage(uri)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajouter_recette)

        //  Initialisation des vues
        etNomRecette = findViewById(R.id.tietNomRecette)
        etIngredients = findViewById(R.id.tietIngredients)
        actvCategorie = findViewById(R.id.actvCategorie)
        btnChoisirImage = findViewById(R.id.ibImageImport)
        recyclerView = findViewById(R.id.rvListeEtapes)

        val isEdition = intent.getBooleanExtra("is_new_recette", true).not()
        val idRecette = intent.getLongExtra("recette_id", -1)

        // Charger la recette existante si en mode édition
        if (isEdition && idRecette != -1L) {
            ajouterOuEditerRecetteViewModel.chargerRecetteExistante(idRecette)

            ajouterOuEditerRecetteViewModel.recetteExistante.observe(this) { recette ->
                recette?.let {
                    etNomRecette.setText(it.nomRecette)
                    etIngredients.setText(it.ingredients)
                    btnChoisirImage.setImageURI(it.image.toUri())
                }
            }

            ajouterOuEditerRecetteViewModel.categorieExistante.observe(this) { categorie ->
                categorie?.let {
                    actvCategorie.setText(it.nom, false)
                }
            }
        }

        // Gestion du bouton Annuler
        val btnAnnuler: Button = findViewById(R.id.btnCancel)
        btnAnnuler.setOnClickListener {
            if (isEdition) {
                Toast.makeText(this,
                    getString(R.string.text_nouvelle_recette_annuler), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this,
                    getString(R.string.text_nouvelle_recette_annuler), Toast.LENGTH_SHORT).show()
            }
            finish()
        }

        // Gestion clavier (pousse la vue vers le haut quand clavier ouvert)
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val imeInsets = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                insets.getInsets(WindowInsetsCompat.Type.ime())
            } else {
                Insets.of(0, 0, 0, insets.systemWindowInsetBottom)
            }
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, imeInsets.bottom)
            WindowInsetsCompat.CONSUMED
        }

        // Gestion des catégories
        ajouterOuEditerRecetteViewModel.categories.observe(this) { listeNomsCategories ->
            val adapterCategories = ArrayAdapter(
                this,
                android.R.layout.simple_dropdown_item_1line,
                listeNomsCategories
            )
            actvCategorie.setAdapter(adapterCategories)
            actvCategorie.dropDownHeight = 600
            actvCategorie.setOnTouchListener { _, _ ->
                actvCategorie.showDropDown()
                false
            }
        }

        //  Drag & Drop pour les étapes
        val touchHelperCallback = object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                return makeMovementFlags(dragFlags, 0)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val fromPos = viewHolder.bindingAdapterPosition
                val toPos = target.bindingAdapterPosition
                ajouterOuEditerRecetteViewModel.deplacerEtape(fromPos, toPos)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

            override fun isLongPressDragEnabled(): Boolean = false
        }
        itemTouchHelper = ItemTouchHelper(touchHelperCallback)

        // Configuration RecyclerView et Adapter
        adapter = EtapeAdapter(
            onRetirerEtape = { position -> ajouterOuEditerRecetteViewModel.supprimerEtape(position) },
            dragStartListener = object : OnStartDragListener {
                override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                    itemTouchHelper.startDrag(viewHolder)
                }
            }
        )
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        itemTouchHelper.attachToRecyclerView(recyclerView)

        ajouterOuEditerRecetteViewModel.listeEtapes.observe(this) { etapes ->
            adapter.submitList(etapes)
        }


        fun focusSurDerniereEtape() {
            val dernierePosition = adapter.itemCount - 1
            recyclerView.scrollToPosition(dernierePosition)
            recyclerView.post {
                val holder = recyclerView.findViewHolderForAdapterPosition(dernierePosition)
                if (holder is EtapeAdapter.EtapeViewHolder) {
                    holder.tietEtape.requestFocus()
                }
            }
        }

        fun recupererDescriptionsEtapes(): List<Etape> {
            val etapesActuelles = mutableListOf<Etape>()
            val listeEtapesVm = ajouterOuEditerRecetteViewModel.listeEtapes.value.orEmpty()

            for (i in 0 until adapter.itemCount) {
                val holder = recyclerView.findViewHolderForAdapterPosition(i) as? EtapeAdapter.EtapeViewHolder
                val description = holder?.tietEtape?.text?.toString() ?: listeEtapesVm.getOrNull(i)?.description.orEmpty()
                val ancienneEtape = listeEtapesVm.getOrNull(i)
                if (ancienneEtape != null) {
                    etapesActuelles.add(
                        ancienneEtape.copy(description = description)
                    )
                }
            }
            return etapesActuelles
        }

        // Observer imageUri pour mise à jour du bouton image
        ajouterOuEditerRecetteViewModel.imageUri.observe(this) { uri ->
            uri?.let {
                btnChoisirImage.setImageURI(it)
            }
        }

        // Bouton Ajouter étape
        val btnAjouterEtape: ImageButton = findViewById(R.id.btnAjouterEtape)
        btnAjouterEtape.setOnClickListener {
            ajouterOuEditerRecetteViewModel.ajouterEtape()
            focusSurDerniereEtape()
        }

        // Bouton Choisir Image
        btnChoisirImage.setOnClickListener {
            imagePickerLauncher.launch("image/*")
        }

        // Bouton Enregistrer
        val btnEnregistrer: Button = findViewById(R.id.btnSave)
        btnEnregistrer.setOnClickListener {

            val etapesActualisees = recupererDescriptionsEtapes()

            ajouterOuEditerRecetteViewModel.mettreAJourEtapes(etapesActualisees)

            ajouterOuEditerRecetteViewModel.enregistrerRecette(
                nom = etNomRecette.text.toString(),
                imageUri = ajouterOuEditerRecetteViewModel.imageUri.value,
                ingredients = etIngredients.text.toString(),
                categorieNom = actvCategorie.text.toString(),
                estNouvelleRecette = !isEdition,
                idRecette = if (isEdition) idRecette else null
            )
        }

        ajouterOuEditerRecetteViewModel.fermerActivity.observe(this) { recetteSaved ->
            if (recetteSaved == true) {
                finish()
            }
        }

        ajouterOuEditerRecetteViewModel.message.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }
}