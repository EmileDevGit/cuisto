package com.moineaux.cuisto.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moineaux.cuisto.R
import com.moineaux.cuisto.data.Ingredient

class AfficherRecetteIngredientsAdapter :
    ListAdapter<Ingredient, AfficherRecetteIngredientsAdapter.AfficherRecetteIngredientsViewHolder>(ingredientsCallBack) {

    companion object {
        private val ingredientsCallBack = object : DiffUtil.ItemCallback<Ingredient>() {
            override fun areItemsTheSame(ancienIngredient: Ingredient, nouvelleIngredient: Ingredient): Boolean {
                return ancienIngredient.id == nouvelleIngredient.id
            }
            override fun areContentsTheSame(ancienIngredient: Ingredient, nouvelleIngredient: Ingredient): Boolean {
                return ancienIngredient == nouvelleIngredient
            }
        }
    }

    inner class AfficherRecetteIngredientsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvIngredient: TextView = itemView.findViewById(R.id.tvNomIngredientRecetteDetail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AfficherRecetteIngredientsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.afficher_recette_detail_ingredients, parent, false)
        return AfficherRecetteIngredientsViewHolder(view)
    }

    override fun onBindViewHolder(holder: AfficherRecetteIngredientsViewHolder, position: Int) {
        val ingredient = getItem(position)
        holder.tvIngredient.text = ingredient.nom
    }
}
