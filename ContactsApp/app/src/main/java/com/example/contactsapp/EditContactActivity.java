package com.example.contactsapp;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditContactActivity extends AppCompatActivity {

    EditText etEditName, etEditPhone;
    Button btnUpdate, btnDelete;
    DatabaseContacts db;
    int contactId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        etEditName = findViewById(R.id.etEditName);
        etEditPhone = findViewById(R.id.etEditPhone);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        db = new DatabaseContacts(this);
        contactId = getIntent().getIntExtra("CONTACT_ID", -1);

        Cursor cursor = db.getAllContacts();
        if (cursor != null && cursor.moveToPosition(contactId)) {
            etEditName.setText(cursor.getString(1));
            etEditPhone.setText(cursor.getString(2));
        }

        btnUpdate.setOnClickListener(v -> {
            String name = etEditName.getText().toString();
            String phone = etEditPhone.getText().toString();
            if (db.updateContact(contactId, name, phone)) {
                Toast.makeText(this, "Contact modifié", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnDelete.setOnClickListener(v -> {
            if (db.deleteContact(contactId)) {
                Toast.makeText(this, "Contact supprimé", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
