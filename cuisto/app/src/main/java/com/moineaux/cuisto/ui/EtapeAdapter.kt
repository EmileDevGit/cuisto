package com.moineaux.cuisto.ui

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.moineaux.cuisto.R
import com.moineaux.cuisto.data.Etape

class EtapeAdapter(
    private val onRetirerEtape: (position: Int) -> Unit,
    private val dragStartListener: OnStartDragListener
) : ListAdapter<Etape, EtapeAdapter.EtapeViewHolder>(EtapeDiffCallback()) {

    private val mapEtapesActuelles = mutableMapOf<Long, String>()

    // Classe contenant les éléments d'une étape (une ligne de le RecyclerView)
    inner class EtapeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tietEtape: TextInputEditText = itemView.findViewById(R.id.tietEtape)
        val btnRetirerEtape: ImageButton = itemView.findViewById(R.id.buttonRetirerEtape)
        val poigneDrag: ImageView = itemView.findViewById(R.id.poigneDrag)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EtapeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_etape, parent, false)
        return EtapeViewHolder(view)
    }


    @SuppressLint("ClickableViewAccessibility") // <-- Supprime le warning inutile
    override fun onBindViewHolder(holder: EtapeViewHolder, position: Int) {
        val etape = getItem(position) // <-- Récupère l'étape à afficher
        val editTextEtape = holder.tietEtape // <-- Récupère le champ d'édition
        editTextEtape.hint = "Étape ${position + 1}"

        (editTextEtape.tag as? TextWatcher)?.let { editTextEtape.removeTextChangedListener(it) } // <-- Supprime le TextWatcher précédent pour éviter les problèmes de synchronisation

        val descriptionActuelle = mapEtapesActuelles[etape.tempid] ?: etape.description
        editTextEtape.setText(descriptionActuelle)

        // Bloc du TextWatcher pour synchroniser les données avec la map avec l'ID de l'étape
        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mapEtapesActuelles[etape.tempid] = s?.toString() ?: ""
            }
            override fun afterTextChanged(s: Editable?) {}
        }
        // Fin du bloc

        editTextEtape.addTextChangedListener(watcher) // <-- Ajoute le TextWatcher au champ d'édition
        editTextEtape.tag = watcher // <-- Stocke le TextWatcher dans le tag du champ d'édition pour le retirer plus tard

        holder.btnRetirerEtape.setOnClickListener {
            val pos = holder.bindingAdapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                val tempid = getItem(pos).tempid // <-- Récupère l'ID de l'étape à retirer
                onRetirerEtape(pos)
                mapEtapesActuelles.remove(tempid)
            }
        }

        holder.poigneDrag.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                dragStartListener.onStartDrag(holder)
                true
            } else false
        }
    }

    fun getCurrentDescriptions(): List<String> {
        return currentList.map { etape ->
            mapEtapesActuelles[etape.tempid] ?: etape.description
        }
    }

}

// Classe permettant de comparer les items de la liste
class EtapeDiffCallback : DiffUtil.ItemCallback<Etape>() {
    override fun areItemsTheSame(ancienneEtape: Etape, nouvelleEtape: Etape): Boolean {
        return ancienneEtape.tempid == nouvelleEtape.tempid
    }
    override fun areContentsTheSame(ancienneEtapeDescription: Etape, nouvelleEtapeDescription: Etape): Boolean {
        return ancienneEtapeDescription == nouvelleEtapeDescription
    }
}

// Interface permettant de démarrer le drag n drop
interface OnStartDragListener {
    fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
}