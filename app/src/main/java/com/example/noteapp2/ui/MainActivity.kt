package com.example.noteapp2.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteapp2.Data.Note
import com.example.noteapp2.ViewModel.NoteViewModel
import com.example.noteapp2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter

    // ✅ Add Note Result Launcher (CLASS LEVEL)
    private val addNoteLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode == Activity.RESULT_OK) {

                val title = result.data?.getStringExtra("title")
                val desc = result.data?.getStringExtra("desc")

                if (!title.isNullOrEmpty() && !desc.isNullOrEmpty()) {
                    val note = Note(title = title, description = desc)
                    noteViewModel.insert(note)

                    Toast.makeText(this, "Note Added", Toast.LENGTH_SHORT).show()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ✅ ViewModel
        noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        // ✅ Adapter with Delete + Edit click
        noteAdapter = NoteAdapter(
            onDeleteClick = { note ->
                noteViewModel.delete(note)
                Toast.makeText(this, "Note Deleted", Toast.LENGTH_SHORT).show()
            },
            onItemClick = { note ->
                val intent = Intent(this, EditNoteActivity::class.java)
                intent.putExtra("id", note.id)
                intent.putExtra("title", note.title)
                intent.putExtra("desc", note.description)
                startActivity(intent)
            }
        )

        // ✅ RecyclerView setup
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = noteAdapter

        // ✅ Observe notes
        noteViewModel.allNotes.observe(this) { list ->
            noteAdapter.setNotes(list)
        }

        // ✅ FAB → AddNoteActivity
        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            addNoteLauncher.launch(intent)
        }
    }
}