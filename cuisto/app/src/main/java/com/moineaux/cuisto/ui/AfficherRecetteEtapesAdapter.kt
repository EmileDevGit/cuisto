package com.moineaux.cuisto.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moineaux.cuisto.R
import com.moineaux.cuisto.data.Etape

class AfficherRecetteEtapesAdapter :
    ListAdapter<Etape, AfficherRecetteEtapesAdapter.EtapeViewHolder>(etapeDiffCallBack) {

    companion object {
        private val etapeDiffCallBack = object : DiffUtil.ItemCallback<Etape>() {
            override fun areItemsTheSame(ancienEtape: Etape, nouvelleEtape: Etape): Boolean {
                // Comparer en utilisant un identifiant unique – supposez que Etape possède un champ 'id'
                return ancienEtape.id == nouvelleEtape.id
            }

            override fun areContentsTheSame(ancienEtape: Etape, nouvelleEtape: Etape): Boolean {
                // Pour les data classes, equals suffit souvent
                return ancienEtape == nouvelleEtape
            }
        }
    }

    inner class EtapeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvEtape: TextView = itemView.findViewById(R.id.tvNomEtapeRecetteDetail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EtapeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.afficher_recette_detail_etapes, parent, false)
        return EtapeViewHolder(view)
    }

    override fun onBindViewHolder(holder: EtapeViewHolder, position: Int) {
        val etape = getItem(position)
        val etapeNumero = (etape.ordre + 1).toString() + ". "
        holder.tvEtape.text = etapeNumero + etape.description
    }
}
