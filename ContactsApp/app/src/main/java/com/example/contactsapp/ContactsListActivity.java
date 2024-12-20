package com.example.contactsapp;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.database.Cursor;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ContactsListActivity extends AppCompatActivity {

    ListView lvContacts;
    DatabaseContacts db;
    ArrayList<String> contactList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

        lvContacts = findViewById(R.id.lvContacts);
        db = new DatabaseContacts(this);
        contactList = new ArrayList<>();

        Cursor cursor = db.getAllContacts();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String contact = cursor.getInt(0) + " - " +
                        cursor.getString(1) + ": " +
                        cursor.getString(2);
                contactList.add(contact);
            }
            cursor.close();
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactList);
        lvContacts.setAdapter(adapter);

        lvContacts.setOnItemClickListener((parent, view, position, id) -> {
            String[] contactDetails = contactList.get(position).split(" - ");
            int contactId = Integer.parseInt(contactDetails[0].trim());
            Intent intent = new Intent(ContactsListActivity.this, EditContactActivity.class);
            intent.putExtra("CONTACT_ID", contactId);
            startActivity(intent);
        });
    }
}
