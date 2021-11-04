package com.example.w6_d4_note_app

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import kotlinx.coroutines.*

import kotlinx.coroutines.Dispatchers.IO

class MainActivity : AppCompatActivity() {

    private val noteDao by lazy { NoteDatabase.getDatabase(this).noteDao() }
    private val repository by lazy { NoteRepository(noteDao) }

    private lateinit var rvNotes: RecyclerView
    private lateinit var editText: EditText
    private lateinit var submitBtn: Button

    private lateinit var notes: List<Notes>
    lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notes = ArrayList()

        editText = findViewById(R.id.messageEditText)
        submitBtn = findViewById(R.id.saveButton)
        submitBtn.setOnClickListener {
            addNote(editText.text.toString())
            editText.text.clear()
            editText.clearFocus()
            

            getItemsList()



        }

        //getItemsList()


        rvNotes = findViewById(R.id.recyclerView)

        adapter = NoteAdapter(this, notes)
        rvNotes.adapter = adapter
        rvNotes.layoutManager = LinearLayoutManager(this)
//        updateRV()


    }

    private fun updateRV(){


        CoroutineScope(Dispatchers.Main).launch {

                adapter.updateAdapter(notes)
                rvNotes.adapter = adapter
                rvNotes.layoutManager = LinearLayoutManager(this@MainActivity)



        }


    }

    private  fun getItemsList(){
        CoroutineScope(IO).launch {
            val data = async {
                repository.getNotes()
            }.await()
            if(data.isNotEmpty()){
                notes = data
                updateRV()
            }else{
                Log.e("MainActivity", "Unable to get data", )
            }
        }
    }

    private fun addNote(noteText: String){
        CoroutineScope(IO).launch {


                repository.addNote(Notes(0, noteText))
            getItemsList()



        }

    }

    private fun editNote(noteID: Int, noteText: String){
        CoroutineScope(IO).launch {
            repository.updateNote(Notes(noteID,noteText))
            getItemsList()
        }
    }

    fun deleteNote(noteID: Int){
        CoroutineScope(IO).launch {
            repository.deleteNote(Notes(noteID,""))
            getItemsList()
        }
    }

    fun raiseDialog(id: Int){
        val dialogBuilder = AlertDialog.Builder(this)
        val updatedNote = EditText(this)
        updatedNote.hint = "Enter new text"
        dialogBuilder
            .setCancelable(false)
            .setPositiveButton("Save", DialogInterface.OnClickListener {
                // The setPositiveButton method takes in two arguments
                // More info here: https://developer.android.com/reference/kotlin/android/app/AlertDialog.Builder#setpositivebutton
                // Use underscores when lambda arguments are not used
                    _, _ ->
                run {
                    editNote(id, updatedNote.text.toString())
                    CoroutineScope(IO).launch {
                        getItemsList()
                        updateRV()


                    }


                }
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, _ -> dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle("Update Note")
        alert.setView(updatedNote)
        alert.show()
    }
}