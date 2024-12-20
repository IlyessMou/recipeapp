package com.example.recipesapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailsActivity extends AppCompatActivity {

    TextView tvTitle, tvIngredients, tvSteps, tvCategory;
    Button btnUpdate, btnDelete;
    DatabaseHelper db;
    int recipeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        tvTitle = findViewById(R.id.tvTitle);
        tvIngredients = findViewById(R.id.tvIngredients);
        tvSteps = findViewById(R.id.tvSteps);
        tvCategory = findViewById(R.id.tvCategory);

        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        db = new DatabaseHelper(this);

        recipeId = getIntent().getIntExtra("recipeId", -1);
        if (recipeId != -1) {
            loadRecipeDetails(recipeId);
        } else {
            Toast.makeText(this, "Invalid recipe ID", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Edit Button Click
        btnUpdate.setOnClickListener(v -> {
            Intent intent = new Intent(RecipeDetailsActivity.this, UpdateRecipeActivity.class);
            intent.putExtra("recipeId", recipeId);
            startActivity(intent);
        });

        // Delete Button Click
        btnDelete.setOnClickListener(v -> {
            deleteRecipe(recipeId);
        });
    }

    private void loadRecipeDetails(int id) {
        Cursor cursor = db.getRecipeById(id);
        if (cursor.moveToFirst()) {
            tvTitle.setText(cursor.getString(1));
            tvIngredients.setText(cursor.getString(2));
            tvSteps.setText(cursor.getString(3));
            tvCategory.setText(cursor.getString(4));
        }
        cursor.close();
    }

    private void deleteRecipe(int id) {
        boolean isDeleted = db.deleteRecipeById(id);
        if (isDeleted) {
            Toast.makeText(this, "Recipe deleted successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to delete recipe", Toast.LENGTH_SHORT).show();
        }
    }
}
