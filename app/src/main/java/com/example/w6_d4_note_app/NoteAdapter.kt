package com.example.w6_d4_note_app

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(
    private val activity: MainActivity,
    private val items: List<Notes>): RecyclerView.Adapter<NoteAdapter.ItemViewHolder>() {

    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NoteAdapter.ItemViewHolder, position: Int) {
        val item = items[position]

        holder.itemView.apply {
            noteTextView.text = item.noteText
            if(position%2==0){
                holderLinearLayout.setBackgroundColor(Color.GRAY)
            }
            ibEditNote.setOnClickListener {
                activity.raiseDialog(item.id)
            }
            ibDeleteNote.setOnClickListener {
                activity.deleteNote(item.id)
            }
        }
    }

    override fun getItemCount() = items.size
}