package com.moineaux.cuisto.ui

import android.os.Build
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moineaux.cuisto.R

class AjouterRecetteActivity : AppCompatActivity() {

// ****** Déclaration de variables ****** //
    private val etapeViewModel: EtapeViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EtapeAdapter
    private lateinit var itemTouchHelper: ItemTouchHelper // <-- Pour gérer le drag n drop des étapes


// ****** Fonctions ****** //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajouter_recette)

        // Bloc de gestion du clavier pour qu'il "pousse" la vue vers le haut
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val imeInsets = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                insets.getInsets(WindowInsetsCompat.Type.ime())
            } else {
                Insets.of(0, 0, 0, insets.systemWindowInsetBottom)
            }

            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                imeInsets.bottom
            )
            WindowInsetsCompat.CONSUMED
        }
        // Fin du bloc

        recyclerView = findViewById(R.id.rvListeEtapes) // <-- Contient les champs d'étapes

        // Bloc de gestion du drag n drop des étapes
        val touchHelperCallback = object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
                val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                return makeMovementFlags(dragFlags, 0)
            }

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                val fromPos = viewHolder.bindingAdapterPosition
                val toPos = target.bindingAdapterPosition
                etapeViewModel.deplacerEtape(fromPos, toPos) // Gestion de la position stockée de l'étape
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
            override fun isLongPressDragEnabled(): Boolean = false
        }
        itemTouchHelper = ItemTouchHelper(touchHelperCallback)
        // Fin du bloc


        adapter = EtapeAdapter(
            onRetirerEtape = { position ->
                etapeViewModel.supprimerEtape(position)
            },
            dragStartListener = object : OnStartDragListener {
                override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                    itemTouchHelper.startDrag(viewHolder)
                }
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        itemTouchHelper.attachToRecyclerView(recyclerView)

        etapeViewModel.listeEtapes.observe(this) { etapes ->
            adapter.submitList(etapes)
        }

        fun focusSurDerniereEtape() {
            val dernierePosition = adapter.itemCount - 1
            recyclerView.scrollToPosition(dernierePosition)
            recyclerView.post { // <-- post permet de s'assurer que le RecyclerView est bien mis à jour avant de faire l'action'
                val holder = recyclerView.findViewHolderForAdapterPosition(dernierePosition)
                if (holder is EtapeAdapter.EtapeViewHolder) {
                    holder.tietEtape.requestFocus()
                }
            }
        }

        val btnAjouterEtape: ImageButton = findViewById(R.id.btnAjouterEtape)
        btnAjouterEtape.setOnClickListener {
            etapeViewModel.ajouterEtape()
            focusSurDerniereEtape()
        }
    }
}