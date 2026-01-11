package com.example.noteapp2.ViewModel


import android.app.Application
import androidx.lifecycle.*
import com.example.noteapp2.Data.Note
import com.example.noteapp2.Data.NoteDatabase
import com.example.noteapp2.Repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NoteRepository
    val allNotes: LiveData<List<Note>>

    init {
        val dao = NoteDatabase.getDatabase(application).noteDao()
        repository = NoteRepository(dao)
        allNotes = repository.allNotes
    }

    fun insert(note: Note) = viewModelScope.launch {
        repository.insert(note)
    }

    fun delete(note: Note) = viewModelScope.launch {
        repository.delete(note)
    }
    fun update(note: Note) = viewModelScope.launch {
        repository.update(note)
    }
}