package com.example.contactsapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText etName, etPhone;
    Button btnAdd, btnViewContacts;
    DatabaseContacts db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        btnAdd = findViewById(R.id.btnAdd);
        btnViewContacts = findViewById(R.id.btnViewContacts);

        db = new DatabaseContacts(this);

        btnAdd.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String phone = etPhone.getText().toString();
            if (name.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            } else {
                if (db.addContact(name, phone)) {
                    Toast.makeText(this, "Contact ajouté avec succès", Toast.LENGTH_SHORT).show();
                    etName.setText("");
                    etPhone.setText("");
                } else {
                    Toast.makeText(this, "Erreur lors de l'ajout", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnViewContacts.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ContactsListActivity.class);
            startActivity(intent);
        });
    }
}
