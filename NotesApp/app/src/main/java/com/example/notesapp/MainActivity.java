package com.example.notesapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNote;
    private Button buttonAddNote, buttonDeleteNote;
    private ListView listViewNotes;

    private ArrayList<String> notes;
    private ArrayAdapter<String> notesAdapter;
    private int selectedNotePosition = -1; // -1 =no note selected

    private SharedPreferences sharedPreferences;
    private static final String NOTES_PREFS = "NotesPrefs";
    private static final String NOTES_KEY = "notesList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNote = findViewById(R.id.editTextNote);
        buttonAddNote = findViewById(R.id.buttonAddNote);
        buttonDeleteNote = findViewById(R.id.buttonDeleteNote);
        listViewNotes = findViewById(R.id.listViewNotes);

        // SharedPreferences
        sharedPreferences = getSharedPreferences(NOTES_PREFS, MODE_PRIVATE);

        notes = new ArrayList<>();
        notesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, notes);
        listViewNotes.setAdapter(notesAdapter);
        listViewNotes.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        loadNotes();

        //note selection
        listViewNotes.setOnItemClickListener((parent, view, position, id) -> {
            if (selectedNotePosition == position) {
                listViewNotes.setItemChecked(position, false);
                selectedNotePosition = -1;
            } else {
                selectedNotePosition = position;
            }
        });

        // Adding note
        buttonAddNote.setOnClickListener(v -> {
            String note = editTextNote.getText().toString().trim();
            if (!note.isEmpty()) {
                notes.add(note);
                notesAdapter.notifyDataSetChanged();
                editTextNote.setText("");
                Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show();
                saveNotes();
            } else {
                Toast.makeText(this, "Please enter a note", Toast.LENGTH_SHORT).show();
            }
        });

        // Deleting note
        buttonDeleteNote.setOnClickListener(v -> {
            if (selectedNotePosition != -1) {
                notes.remove(selectedNotePosition);
                notesAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show();
                selectedNotePosition = -1;
                saveNotes();
            } else {
                Toast.makeText(this, "No note selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //saving notes to SharedPreferences
    private void saveNotes() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(NOTES_KEY, new HashSet<>(notes));
        editor.apply();
    }

    //loading notes from SharedPreferences
    private void loadNotes() {
        Set<String> savedNotes = sharedPreferences.getStringSet(NOTES_KEY, new HashSet<>());
        notes.clear();
        notes.addAll(savedNotes);
        notesAdapter.notifyDataSetChanged();
    }
}

