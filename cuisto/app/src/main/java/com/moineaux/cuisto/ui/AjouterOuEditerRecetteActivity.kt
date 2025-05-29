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

        // 1. Initialisation des vues AVANT tout usage
        etNomRecette = findViewById(R.id.tietNomRecette)
        etIngredients = findViewById(R.id.tietIngredients)
        actvCategorie = findViewById(R.id.actvCategorie)
        btnChoisirImage = findViewById(R.id.ibImageImport)
        recyclerView = findViewById(R.id.rvListeEtapes)

        val isEdition = intent.getBooleanExtra("is_new_recette", true).not()
        val idRecette = intent.getLongExtra("recette_id", -1)

        // 2. Charger la recette existante si édition
        if (isEdition && idRecette != -1L) {
            ajouterOuEditerRecetteViewModel.chargerRecetteExistante(idRecette)

            // 3. Observer les LiveData APRÈS initialisation des vues
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

        // 4. Gestion du bouton Annuler
        val btnAnnuler: Button = findViewById(R.id.btnCancel)
        btnAnnuler.setOnClickListener {
            Toast.makeText(this,
                getString(R.string.text_nouvelle_recette_annuler), Toast.LENGTH_SHORT).show()
            finish()
        }

        // 5. Gestion clavier (pousse la vue vers le haut quand clavier ouvert)
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

        // 6. Gestion des catégories (AutoCompleteTextView)
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

        // 7. Drag & Drop pour les étapes
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

        // 8. Configuration RecyclerView et Adapter
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

        // 9. Observer la liste des étapes et soumettre à l'adapter
        ajouterOuEditerRecetteViewModel.listeEtapes.observe(this) { etapes ->
            adapter.submitList(etapes)
        }

        // 10. Fonctions utilitaires internes
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

        // 11. Observer imageUri pour mise à jour du bouton image
        ajouterOuEditerRecetteViewModel.imageUri.observe(this) { uri ->
            uri?.let {
                btnChoisirImage.setImageURI(it)
            }
        }

        // 12. Bouton Ajouter étape
        val btnAjouterEtape: ImageButton = findViewById(R.id.btnAjouterEtape)
        btnAjouterEtape.setOnClickListener {
            ajouterOuEditerRecetteViewModel.ajouterEtape()
            focusSurDerniereEtape()
        }

        // 13. Bouton Choisir Image
        btnChoisirImage.setOnClickListener {
            imagePickerLauncher.launch("image/*")
        }

        // 14. Bouton Enregistrer
        val btnEnregistrer: Button = findViewById(R.id.btnSave)
        btnEnregistrer.setOnClickListener {
            // 1. Récupérer descriptions des étapes depuis ViewHolders
            val etapesActualisees = recupererDescriptionsEtapes()

            // 2. Mettre à jour la liste des étapes dans le ViewModel
            ajouterOuEditerRecetteViewModel.mettreAJourEtapes(etapesActualisees)

            // 3. Lancer l'enregistrement
            ajouterOuEditerRecetteViewModel.enregistrerRecette(
                nom = etNomRecette.text.toString(),
                imageUri = ajouterOuEditerRecetteViewModel.imageUri.value,
                ingredients = etIngredients.text.toString(),
                categorieNom = actvCategorie.text.toString(),
                estNouvelleRecette = !isEdition,
                idRecette = if (isEdition) idRecette else null
            )
        }

        // 15. Observer la fermeture de l'activité après enregistrement
        ajouterOuEditerRecetteViewModel.fermerActivity.observe(this) { recetteSaved ->
            if (recetteSaved == true) {
                val intent = Intent(this, ToutesRecettesActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        // 16. Observer messages Toast
        ajouterOuEditerRecetteViewModel.message.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }
}