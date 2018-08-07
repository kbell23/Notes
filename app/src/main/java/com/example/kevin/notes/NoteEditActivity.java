package com.example.kevin.notes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class NoteEditActivity extends AppCompatActivity {
    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);

        EditText editText = findViewById(R.id.editText);

        // grabs what note the user picked so we can edit the text
        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId", -1);

        // sanity check to see if the note exists in the first place
        if (noteId != -1){
            editText.setText(MainActivity.notes.get(noteId));
        }else{
            // add a new note
            MainActivity.notes.add("");
            noteId = MainActivity.notes.size() - 1;
        }

        // in the event where someone changes the text of the note they clicked
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.notes.set(noteId, String.valueOf(s));
                MainActivity.arrayAdapter.notifyDataSetChanged();

                // stored data for edited notes
                SharedPreferences sharedPreferences = getApplicationContext()
                        .getSharedPreferences("com.example.kevin.notes", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet<>(MainActivity.notes);
                sharedPreferences.edit().putStringSet("notes", set).apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
