package com.example.recipesapp;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateRecipeActivity extends AppCompatActivity {

    EditText etTitle, etIngredients, etSteps;
    Spinner spinnerCategory;
    Button btnUpdate, btnDelete;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_recipe);

        etTitle = findViewById(R.id.etTitle);
        etIngredients = findViewById(R.id.etIngredients);
        etSteps = findViewById(R.id.etSteps);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        db = new DatabaseHelper(this);

        int recipeId = getIntent().getIntExtra("recipeId", -1);
        if (recipeId != -1) {
            loadRecipeDetails(recipeId);
        } else {
            Toast.makeText(this, "Invalid recipe ID", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Update button click listener
        btnUpdate.setOnClickListener(v -> {
            String title = etTitle.getText().toString();
            String ingredients = etIngredients.getText().toString();
            String steps = etSteps.getText().toString();
            String category = spinnerCategory.getSelectedItem().toString();

            if (title.isEmpty() || ingredients.isEmpty() || steps.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            } else {
                if (db.updateRecipe(recipeId, title, ingredients, steps, category)) {
                    Toast.makeText(this, "Recette mise à jour avec succès", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Erreur lors de la mise à jour", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Delete button click listener
        btnDelete.setOnClickListener(v -> {
            db.deleteRecipe(recipeId);
            Toast.makeText(this, "Recette supprimée avec succès", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    //load recipe details
    private void loadRecipeDetails(int id) {
        Cursor cursor = db.getRecipeById(id);
        if (cursor != null && cursor.moveToFirst()) {
            etTitle.setText(cursor.getString(1));
            etIngredients.setText(cursor.getString(2));
            etSteps.setText(cursor.getString(3));

            String category = cursor.getString(4);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.categories, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCategory.setAdapter(adapter);

            int position = adapter.getPosition(category);
            spinnerCategory.setSelection(position);
        }
        if (cursor != null) {
            cursor.close();
        }
    }
}
