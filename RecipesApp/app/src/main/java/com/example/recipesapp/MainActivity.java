package com.example.recipesapp;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;
    ListView listView;
    FloatingActionButton fabAdd;
    ArrayList<String> recipesList;
    ArrayList<Integer> recipeIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        listView = findViewById(R.id.listView);
        fabAdd = findViewById(R.id.fabAdd);

        recipesList = new ArrayList<>();
        recipeIds = new ArrayList<>();

        loadRecipes();

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddRecipeActivity.class);
            startActivity(intent);
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(MainActivity.this, RecipeDetailsActivity.class);
            intent.putExtra("recipeId", recipeIds.get(position));
            startActivity(intent);
        });
    }

    private void loadRecipes() {
        recipesList.clear();
        recipeIds.clear();

        Cursor cursor = db.getAllRecipes();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Aucune recette trouvée", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                recipeIds.add(cursor.getInt(0));
                recipesList.add(cursor.getString(1));
             }
         }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recipesList);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRecipes();
    }
}
