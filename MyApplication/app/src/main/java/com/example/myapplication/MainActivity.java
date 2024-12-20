package com.example.myapplication;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText editTextPrenom;
    private Button buttonEnvoyer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Liaison des éléments de l'interface utilisateur
        editTextPrenom = findViewById(R.id.editTextText);
        buttonEnvoyer = findViewById(R.id.ButtonCancel);

        // Définition de l'action du bouton
        buttonEnvoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String prenom = editTextPrenom.getText().toString();
                Toast.makeText(MainActivity.this, prenom, Toast.LENGTH_SHORT).show();
            }
        });
    }
}