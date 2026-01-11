package com.example.noteapp2.Repository

import com.example.noteapp2.Data.NoteDao
import com.example.noteapp2.Data.Note

class NoteRepository(private val noteDao: NoteDao) {

    val allNotes = noteDao.getAllNotes()

    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    suspend fun delete(note: Note) {
        noteDao.delete(note)
    }
    suspend fun update(note: Note) {
        noteDao.update(note)
    }
}