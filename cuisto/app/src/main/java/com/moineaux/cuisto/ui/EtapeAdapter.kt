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
    private val onEtapeDelete: (position: Int) -> Unit,
    private val dragStartListener: OnStartDragListener
) : ListAdapter<Etape, EtapeAdapter.EtapeViewHolder>(EtapeDiffCallback()) {

    private val currentTexts = mutableMapOf<Long, String>()

    inner class EtapeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tietEtape: TextInputEditText = itemView.findViewById(R.id.tietEtape)
        val btnRetirerEtape: ImageButton = itemView.findViewById(R.id.buttonRetirerEtape)
        val handleDrag: ImageView = itemView.findViewById(R.id.dragHandle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EtapeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_etape, parent, false)
        return EtapeViewHolder(view)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: EtapeViewHolder, position: Int) {
        val etape = getItem(position)
        val editText = holder.tietEtape
        editText.hint = "Étape ${position + 1}"

        (editText.tag as? TextWatcher)?.let { editText.removeTextChangedListener(it) }

        val currentText = currentTexts[etape.id] ?: etape.description
        editText.setText(currentText)

        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                currentTexts[etape.id] = s?.toString() ?: ""
            }
            override fun afterTextChanged(s: Editable?) {}
        }

        editText.addTextChangedListener(watcher)
        editText.tag = watcher

        holder.btnRetirerEtape.setOnClickListener {
            val pos = holder.bindingAdapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                val id = getItem(pos).id
                onEtapeDelete(pos)
                currentTexts.remove(id)
            }
        }

        holder.handleDrag.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                dragStartListener.onStartDrag(holder)
                true
            } else false
        }
    }

    fun getCurrentDescriptions(): List<String> {
        return currentList.map { etape ->
            currentTexts[etape.id] ?: etape.description
        }
    }
}

class EtapeDiffCallback : DiffUtil.ItemCallback<Etape>() {
    override fun areItemsTheSame(oldItem: Etape, newItem: Etape): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Etape, newItem: Etape): Boolean {
        return oldItem == newItem
    }
}

interface OnStartDragListener {
    fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
}