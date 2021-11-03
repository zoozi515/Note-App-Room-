package com.example.w6_d4_note_app

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.w6_d4_note_app.MainActivity
import com.example.w6_d4_note_app.Notes
import com.example.w6_d4_note_app.databinding.ItemRowBinding

class NoteAdapter(
    private val activity: MainActivity,
    private var items: List<Notes>): RecyclerView.Adapter<NoteAdapter.ItemViewHolder>() {

    class ItemViewHolder(val binding: ItemRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int){
        val item = items[position]

        holder.binding.apply {
            noteTextView.text = item.noteText /*set text view from item row = noteText column from Notes class*/
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

    fun updateAdapter(newNote: List<Notes>)
    {
        items = newNote
        this.notifyDataSetChanged()
    }
}