package com.example.recipesapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddRecipeActivity extends AppCompatActivity {

    EditText etTitle, etIngredients, etSteps;
    Spinner spinnerCategory;
    Button btnSave;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        etTitle = findViewById(R.id.etTitle);
        etIngredients = findViewById(R.id.etIngredients);
        etSteps = findViewById(R.id.etSteps);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        btnSave = findViewById(R.id.btnSave);
        db = new DatabaseHelper(this);

        // Categories
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        // Save button click listener
        btnSave.setOnClickListener(v -> {
            String title = etTitle.getText().toString();
            String ingredients = etIngredients.getText().toString();
            String steps = etSteps.getText().toString();
            String category = spinnerCategory.getSelectedItem().toString();

            if (title.isEmpty() || ingredients.isEmpty() || steps.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                boolean result = db.addRecipe(title, ingredients, steps, category);
                if (result) {
                    Toast.makeText(this, "Recipe added successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Error adding recipe", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

