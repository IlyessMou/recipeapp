package com.example.recipesapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "recipes.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_RECIPES = "recipes";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_INGREDIENTS = "ingredients";
    private static final String COLUMN_STEPS = "steps";
    private static final String COLUMN_CATEGORY = "category";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_RECIPES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_INGREDIENTS + " TEXT, "
                + COLUMN_STEPS + " TEXT, "
                + COLUMN_CATEGORY + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        onCreate(db);
    }

    public boolean addRecipe(String title, String ingredients, String steps, String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_INGREDIENTS, ingredients);
        values.put(COLUMN_STEPS, steps);
        values.put(COLUMN_CATEGORY, category);

        long result = db.insert(TABLE_RECIPES, null, values);
        db.close();
        return result != -1;
    }

    public Cursor getAllRecipes() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_RECIPES, null);
    }

    public Cursor getRecipeById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_RECIPES + " WHERE " + COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public boolean updateRecipe(int id, String title, String ingredients, String steps, String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_INGREDIENTS, ingredients);
        values.put(COLUMN_STEPS, steps);
        values.put(COLUMN_CATEGORY, category);

        int result = db.update(TABLE_RECIPES, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    public void deleteRecipe(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RECIPES, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public boolean deleteRecipeById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("recipes", "id = ?", new String[]{String.valueOf(id)}) > 0;
    }

}
