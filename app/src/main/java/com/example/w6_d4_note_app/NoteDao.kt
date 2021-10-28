package com.example.w6_d4_note_app

import androidx.room.*

@Dao
interface NoteDao {

    @Query("SELECT * FROM Notes ORDER BY id ASC")
    fun viewNotes(): List<Notes>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(note: Notes)

    @Delete
    suspend fun deleteNote(note: Notes)

    @Update
    suspend fun updateNote(note: Notes)
}