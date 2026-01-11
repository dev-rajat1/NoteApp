package com.example.noteapp2.ui

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.noteapp2.Data.Note
import com.example.noteapp2.R
import com.example.noteapp2.ViewModel.NoteViewModel
import com.example.noteapp2.databinding.ActivityEditNoteBinding
import com.example.noteapp2.ui.EditNoteActivity

class EditNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditNoteBinding
    private lateinit var noteViewModel: NoteViewModel
    private var noteId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        // 🟢 OLD DATA RECEIVE
        noteId = intent.getIntExtra("id", 0)
        binding.etTitle.setText(intent.getStringExtra("title"))
        binding.etDesc.setText(intent.getStringExtra("desc"))

        binding.btnUpdate.setOnClickListener {
            updateNote()
        }
    }

    private fun updateNote() {
        val title = binding.etTitle.text.toString().trim()
        val desc = binding.etDesc.text.toString().trim()


        if (title.isEmpty() || desc.isEmpty()) {
            Toast.makeText(this, "FIELDS CAN'T BE EMPTY", Toast.LENGTH_SHORT).show()
            return
        }

        val updatedNote = Note(
            id = noteId,   // ⭐ SAME ID VERY IMPORTANT
            title = title,
            description = desc
        )
       

        noteViewModel.update(updatedNote)
        Toast.makeText(this, "NOTE UPDATED", Toast.LENGTH_SHORT).show()
        finish()
    }
}