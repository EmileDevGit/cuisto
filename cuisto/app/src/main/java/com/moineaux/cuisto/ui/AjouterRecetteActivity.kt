package com.moineaux.cuisto.ui

import android.annotation.SuppressLint
import android.app.Application
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

class AjouterRecetteActivity() : AppCompatActivity() {

    // Variables à initialiser plus tard
    private lateinit var etNomRecette: EditText
    private lateinit var etIngredients: EditText
    private lateinit var actvCategorie: AutoCompleteTextView

    private val ajouterRecetteViewModel: AjouterRecetteViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EtapeAdapter
    private lateinit var itemTouchHelper: ItemTouchHelper
    private lateinit var btnChoisirImage: ImageButton
    private lateinit var btnEnregistrer: Button


    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            btnChoisirImage.setImageURI(uri)
            ajouterRecetteViewModel.definirImage(uri)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajouter_recette)

        // Initialisation des vues APRÈS setContentView
        etNomRecette = findViewById(R.id.tietNomRecette)
        etIngredients = findViewById(R.id.tietIngredients)
        actvCategorie = findViewById(R.id.actvCategorie)
        btnChoisirImage = findViewById(R.id.ibImageImport)
        recyclerView = findViewById(R.id.rvListeEtapes)

        // Gestion du bouton Annuler
        val btnAnnuler: Button = findViewById(R.id.btnCancel)
        btnAnnuler.setOnClickListener {
            Toast.makeText(this, "Nouvelle recette annulée", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Gestion clavier "push" la vue vers le haut
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
        ajouterRecetteViewModel.categories.observe(this) { listeNomsCategories ->
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

        // Drag & drop des étapes
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
                ajouterRecetteViewModel.deplacerEtape(fromPos, toPos)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

            override fun isLongPressDragEnabled(): Boolean = false
        }
        itemTouchHelper = ItemTouchHelper(touchHelperCallback)

        // Gestion étapes affichage
        adapter = EtapeAdapter(
            onRetirerEtape = { position -> ajouterRecetteViewModel.supprimerEtape(position) },
            dragStartListener = object : OnStartDragListener {
                override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                    itemTouchHelper.startDrag(viewHolder)
                }
            }
        )
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        itemTouchHelper.attachToRecyclerView(recyclerView)

        ajouterRecetteViewModel.listeEtapes.observe(this) { etapes ->
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
            val listeEtapesVm = ajouterRecetteViewModel.listeEtapes.value.orEmpty()

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

        // Gestion image
        ajouterRecetteViewModel.imageUri.observe(this) { uri ->
            uri?.let {
                btnChoisirImage.setImageURI(it)
            }
        }


        val btnAjouterEtape: ImageButton = findViewById(R.id.btnAjouterEtape)
        btnAjouterEtape.setOnClickListener {
            ajouterRecetteViewModel.ajouterEtape()
            focusSurDerniereEtape()
        }

        btnChoisirImage.setOnClickListener {
            imagePickerLauncher.launch("image/*")
        }

        // Enregistrement de la recette
        val btnEnregistrer: Button = findViewById(R.id.btnSave)

        btnEnregistrer.setOnClickListener {
            // 1. Récupérer les descriptions actuelles des étapes depuis les ViewHolders
            val etapesActualisees = recupererDescriptionsEtapes()

            // 2. Mettre à jour la liste des étapes dans le ViewModel
            ajouterRecetteViewModel.mettreAJourEtapes(etapesActualisees)

            // 3. Lancer l'enregistrement avec la liste à jour
            ajouterRecetteViewModel.enregistrerRecette(
                etNomRecette.text.toString(),
                ajouterRecetteViewModel.imageUri.value,
                etIngredients.text.toString(),
                actvCategorie.text.toString()
            )
        }

        ajouterRecetteViewModel.fermerActivity.observe(this) { recetteSaved ->
            if (recetteSaved == true) {
                val intent = Intent(this, ToutesRecettesActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        // Gestion messages Toast
        ajouterRecetteViewModel.message.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }
}