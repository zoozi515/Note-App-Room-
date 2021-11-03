package com.example.w6_d4_note_app

class NoteRepository(private val noteDao: NoteDao) {

    val getNotes: ArrayList<Notes> = noteDao.viewNotes()

    suspend fun addNote(note: Notes){
        noteDao.addNote(note)
    }

    suspend fun updateNote(note: Notes){
        noteDao.updateNote(note)
    }

    suspend fun deleteNote(note: Notes){
        noteDao.deleteNote(note)
    }

}