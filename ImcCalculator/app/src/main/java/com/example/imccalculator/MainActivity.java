package com.example.imccalculator;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etPrenom, etPoids, etTaille;
    private RadioGroup rgSexe;
    private TextView tvResultat;
    private Button btnSend, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //views
        etPrenom = findViewById(R.id.etPrenom);
        etPoids = findViewById(R.id.etPoids);
        etTaille = findViewById(R.id.etTaille);
        rgSexe = findViewById(R.id.rgSexe);
        tvResultat = findViewById(R.id.tvResultat);
        btnSend = findViewById(R.id.btnSend);
        btnCancel = findViewById(R.id.btnCancel);

        //click listener
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateIMC();
            }
        });

        //Cancel button
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFields();
            }
        });
    }

    //calculate IMC
    private void calculateIMC() {
        String prenom = etPrenom.getText().toString().trim();
        String poidsStr = etPoids.getText().toString().trim();
        String tailleStr = etTaille.getText().toString().trim();

        if (prenom.isEmpty() || poidsStr.isEmpty() || tailleStr.isEmpty()) {
            Toast.makeText(MainActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        double poids = Double.parseDouble(poidsStr);
        double taille = Double.parseDouble(tailleStr);

        //Calculate BMI
        double imc = poids / (taille * taille);

        // Display the result
        tvResultat.setText("Votre IMC est: " + String.format("%.2f", imc));

        //feedback
        String sexe = ((RadioButton) findViewById(rgSexe.getCheckedRadioButtonId())).getText().toString();
        if (imc < 18.5) {
            Toast.makeText(this, sexe +" "+ prenom+ ", vous êtes en sous-poids.", Toast.LENGTH_SHORT).show();
        } else if (imc < 24.9) {
            Toast.makeText(this, sexe +" "+ prenom+ ", votre poids est normal.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, sexe +" "+ prenom+ ", vous êtes en surpoids.", Toast.LENGTH_SHORT).show();
        }
    }

    // reset all fields
    private void resetFields() {
        etPrenom.setText("");
        etPoids.setText("");
        etTaille.setText("");
        rgSexe.clearCheck();
        tvResultat.setText("");
    }
}


